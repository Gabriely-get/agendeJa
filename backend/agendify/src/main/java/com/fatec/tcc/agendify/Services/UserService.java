package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.*;
import com.fatec.tcc.agendify.Entities.RequestTemplate.CompanyBranchBody;
import com.fatec.tcc.agendify.Entities.RequestTemplate.UserBody;
import com.fatec.tcc.agendify.Entities.RequestTemplate.UserFields;
import com.fatec.tcc.agendify.Repositories.Address.AddressRepository;
import com.fatec.tcc.agendify.Repositories.PortfolioRepository;
import com.fatec.tcc.agendify.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(User.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyBranchService companyBranchService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BusinessHourService businessHourService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByEmail(username);
    }

    public UserFields getUserById(Long id) throws IOException {
        System.out.println("ID USER:::"+id);
        assert id != null;

        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Image image = null;
            Image cover = null;

            System.out.println("ID IMAGE:::"+user.getImageCoverId());
            System.out.println("ID IMAGE:::"+user.getImageProfileId());
            if (user.getImageProfileId() != null) {
                image = this.imageService.getImage(user.getImageProfileId());
            }

            if (user.getIsJobProvider()) {
                if (user.getImageCoverId() != null)
                    cover = this.imageService.getImage(user.getImageCoverId());
            }

            return new UserFields(
                    user,
                    image == null ? "" : image.getBase64(),
                    cover == null ? "" : cover.getBase64()
            );
        }

        throw new NotFoundException("User does not exists");
    }

    public List<User> getAllUsers() {
        Iterable<User> iterableUsers = this.userRepository.findAll();
        List<User> userList = new ArrayList<>();
        iterableUsers.forEach(userList::add);

        return userList;
    }

    public List<User> getAllUsersEnterprise() {
        return this.userRepository.findAllByRole_Value(Role.ENTERPRISE);
    }

    public List<User> getActiveUsers() {
        List<User> users = this.userRepository.findAll();

        return users.stream().filter(User::getIsActive).toList();
    }

    public UserFields createUserEnterprise(UserBody user) throws SQLIntegrityConstraintViolationException {
        boolean userAlreadyExistsByCpf = this.userRepository.existsUserByCpf(user.getCpf());
        boolean userAlreadyExistsByEmail = this.userRepository.existsUserByEmail(user.getEmail());
        boolean userAlreadyExistsByPhone = this.userRepository.existsUserByPhone(user.getPhone());

        Image imageProfile = new Image();
        Image imageCover = new Image();
        User newUser;

        try {
            if (userAlreadyExistsByCpf) {
                throw new SQLIntegrityConstraintViolationException("CPF is already registered");
            }
            if (userAlreadyExistsByEmail) {
                throw new SQLIntegrityConstraintViolationException("E-mail is already registered");
            }
            if (userAlreadyExistsByPhone) {
                throw new SQLIntegrityConstraintViolationException("Phone is already registered");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setIsActive(true);
            user.setRole(Role.ENTERPRISE);

            if (Objects.nonNull(user.getProfileImage())) {
                imageProfile = this.imageService.saveImage(user.getProfileImage());
                user.setImageProfileId(imageProfile.getId());
            }

            if (Objects.nonNull(user.getCoverImage())) {
                imageCover = this.imageService.saveImage(user.getCoverImage());
                user.setImageCoverId(imageCover.getId());
            }
            newUser = this.userRepository.save(new User(user));

//            this.companyBranchService.createCompanyBranchWithCategoriesAndSubcategories(
//                    new CompanyBranchBody(
//                            user.getFantasyName(),
//                            newUser.getId(),
//                            user.getAddress(),
//                            user.getCategory(),
//                            user.getSubCategories()
//                    )
//            );

            CompanyBranchBody companyBranchBody = new CompanyBranchBody(
                    user.getFantasyName(),
                    newUser.getId(),
                    user.getAddress(),
                    user.getCategory(),
                    user.getSubCategories(),
                    user.getDescription(),
                    user.getIs24Hours()
            );

            CompanyBranch companyCreated =
                    this.companyBranchService.create(
                            companyBranchBody
                    );

            Portfolio newPortfolio = this.portfolioService.createPortfolio(
                    companyCreated.getId(),
                    companyBranchBody.getCategory(),
                    companyBranchBody.getSubCategories());

            this.businessHourService.register(user.getIs24Hours(), user.getHours(), newPortfolio);

            System.out.println("HERE:::");
            return new UserFields(newUser, imageProfile.getBase64(), imageCover.getBase64());

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("User register unexpected error: " + e.getMessage());
            throw e;
        }
    }

    public UserFields createUserClient(UserBody user) throws SQLIntegrityConstraintViolationException, IOException {
        boolean userAlreadyExistsByCpf = this.userRepository.existsUserByCpf(user.getCpf());
        boolean userAlreadyExistsByEmail = this.userRepository.existsUserByEmail(user.getEmail());
        boolean userAlreadyExistsByPhone = this.userRepository.existsUserByPhone(user.getPhone());
        Image imageProfile = new Image();
        User userReturn;

        try {
            if (userAlreadyExistsByCpf) {
                throw new SQLIntegrityConstraintViolationException("CPF is already registered");
            }
            if (userAlreadyExistsByEmail) {
                throw new SQLIntegrityConstraintViolationException("E-mail is already registered");
            }
            if (userAlreadyExistsByPhone) {
                throw new SQLIntegrityConstraintViolationException("Phone is already registered");
            }

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setIsActive(true);
            user.setRole(Role.USER);

            if (Objects.nonNull(user.getProfileImage())) {
                imageProfile = this.imageService.saveImage(user.getProfileImage());

                user.setImageProfileId(imageProfile.getId());
            }
            userReturn =  this.userRepository.save(new User(user));

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("User register unexpected error: " + e.getMessage());
            throw e;
        }

        return new UserFields(userReturn, imageProfile.getBase64());
    }

    public void updateUser(Long id, UserBody user) {
        //TODO
        // save user picture too
        // validate date birth day
        //DONE verify if new phone already exists
        //DONE paginação
        //DONE ordenação
        //DONE busca (ex: silva, quem tem silva no nome?)

        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isEmpty()) throw new NotFoundException("User does not exists");
        User userToUpdate = optionalUser.get();

        if (Objects.nonNull(user.getFirstName())) {
            if (user.getFirstName().length() < 2)
                throw new IllegalUserArgumentException("Invalid username length!");

            userToUpdate.setFirstName(user.getFirstName().trim());
        }

        if ( Objects.nonNull(user.getLastName()))
            if (user.getLastName().length() > 1)
                userToUpdate.setLastName(user.getLastName().trim());
            else
                throw new IllegalUserArgumentException("Invalid lastname length!");

        if (Objects.nonNull(user.getPhone())) {
            if (user.getPhone().length() != 11)
                throw new IllegalUserArgumentException("Invalid phone number!");

            if (!user.getPhone().matches("[0-9]+"))
                throw new IllegalUserArgumentException("Invalid phone!");

            userToUpdate.setPhone(user.getPhone().trim());
        }

        if (Objects.nonNull(user.getProfileImage())) {
            Image newProfileImage = this.imageService.saveImage(user.getProfileImage());
            userToUpdate.setImageProfileId(newProfileImage.getId());
        } else {
            userToUpdate.setImageProfileId(null);
        }

        if (userToUpdate.getIsJobProvider()) {
            if (Objects.nonNull(user.getCoverImage())) {
                Image newCoverImage = this.imageService.saveImage(user.getCoverImage());
                userToUpdate.setImageCoverId(newCoverImage.getId());
            } else
                userToUpdate.setImageCoverId(null);
        }

        userToUpdate.setUpdateAt(Timestamp.from(Instant.now()));
        this.userRepository.save(userToUpdate);
    }

    public void deleteUser(Long id) {
        boolean userExists = this.userRepository.existsById(id);

        if (userExists) {
            User user = this.userRepository.findById(id).get();
            user.setIsActive(false);
            this.userRepository.save(user);
        }

    }

    public Long getIdByEmail(String email) {
        return this.userRepository.getUserByEmail(email).getId();
    }
}

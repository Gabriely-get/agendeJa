package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.*;
import com.fatec.tcc.agendify.Entities.RequestTemplate.CompanyBranchBody;
import com.fatec.tcc.agendify.Entities.RequestTemplate.UserBody;
import com.fatec.tcc.agendify.Repositories.Address.AddressRepository;
import com.fatec.tcc.agendify.Repositories.PortfolioRepository;
import com.fatec.tcc.agendify.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserService {
    Logger logger = LoggerFactory.getLogger(User.class);

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private RoleRepository roleRepository;

//    @Autowired
//    private ImageDataService imageDataService;

    @Autowired
    private CompanyBranchService companyBranchService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    public UserDetails getUserById(Long id) throws IOException {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Image image = this.imageService.getImage(user.getImageProfileId());
            Image cover;

            if (user.getIsJobProvider()) {
                cover = this.imageService.getImage(user.getImageCoverId());
                return new UserDetails(user, image.getBase64(), cover.getBase64());
            }


            //if (user.getIsActive())
            return new UserDetails(user, image.getBase64());
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

    public UserDetails createUser(UserBody user) throws SQLIntegrityConstraintViolationException, IOException {
        BCryptPasswordEncoder bCryptPasswordEncoder;
        boolean userAlreadyExistsByCpf = this.userRepository.existsUserByCpf(user.getCpf());
        boolean userAlreadyExistsByEmail = this.userRepository.existsUserByEmail(user.getEmail());
        boolean userAlreadyExistsByPhone = this.userRepository.existsUserByPhone(user.getPhone());
        Image imageProfile = new Image();
        Image imageCover = new Image();
        User userId;
        User userReturn = new User();
        Portfolio portfolio = new Portfolio();

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

            bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashedPass = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(hashedPass);

            user.setIsActive(true);
            if (Objects.nonNull(user.getProfileImage())) {
                imageProfile = this.imageService.saveImage(user.getProfileImage());

                user.setImageProfileId(imageProfile.getId());
            }
            if (user.getIsJobProvider()) {
                user.setRole(Role.ENTERPRISE);

                if (Objects.nonNull(user.getCoverImage())) {
                    System.out.println("NON NULL::" + user.getCoverImage());
                    imageCover = this.imageService.saveImage(user.getCoverImage());

                    user.setImageCoverId(imageCover.getId());
                }
                userId = this.userRepository.save(new User(user));

                this.companyBranchService.createCompanyBranchWithCategoriesAndSubcategories(
                        new CompanyBranchBody(
                                user.getFantasyName(),
                                userId.getId(),
                                user.getAddress(),
                                user.getCategory(),
                                user.getSubCategories()
                        )
                );

                return new UserDetails(userId, imageProfile.getBase64(), imageCover.getBase64());
            } else {
                user.setRole(Role.USER);
                userReturn =  this.userRepository.save(new User(user));
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("User register unexpected error: " + e.getMessage());
            throw e;
        }

        return new UserDetails(userReturn, imageProfile.getBase64());
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

}

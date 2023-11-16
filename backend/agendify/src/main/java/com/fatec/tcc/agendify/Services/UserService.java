package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.Portfolio;
import com.fatec.tcc.agendify.Entities.RequestTemplate.CompanyBranchBody;
import com.fatec.tcc.agendify.Entities.RequestTemplate.UserBody;
import com.fatec.tcc.agendify.Entities.Role;
import com.fatec.tcc.agendify.Entities.User;
import com.fatec.tcc.agendify.Repositories.Address.AddressRepository;
import com.fatec.tcc.agendify.Repositories.PortfolioRepository;
import com.fatec.tcc.agendify.Repositories.UserRepository;
import org.apache.logging.log4j.util.Strings;
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
    private AddressService addressService;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    public User getUserById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            //if (user.getIsActive())
                return user;
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

    public void createUser(UserBody user) throws SQLIntegrityConstraintViolationException, IOException {
        BCryptPasswordEncoder bCryptPasswordEncoder;
        boolean userAlreadyExistsByCpf = this.userRepository.existsUserByCpf(user.getCpf());
        boolean userAlreadyExistsByEmail = this.userRepository.existsUserByEmail(user.getEmail());
        boolean userAlreadyExistsByPhone = this.userRepository.existsUserByPhone(user.getPhone());
        User userId = new User();
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

//            if (Objects.nonNull(user.getFile())) {
//                Long id = this.imageDataService.uploadImage(user.getFile(), null);
//                user.setImageId(id);
//            }

            bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String hashedPass = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(hashedPass);

            user.setIsActive(true);
            if (Objects.isNull(user.getImageId())) {
                user.setImageId(0L);
                logger.info("Image as null");
            }
            if (user.getIsJobProvider()) {
//                Role role = this.roleRepository.findByName("ENTERPRISE");
//                user.addRole(role);
                user.setRole(Role.ENTERPRISE);

                userId = this.userRepository.save(new User(user));
//                if (Objects.isNull(user.getAddress())) {
//                    this.companyBranchService.createCompanyBranchWithCategoriesAndSubcategories(
//                            new CompanyBranchBody(
//                                    user.getFantasyName(),
//                                    userId.getId(),
//                                    null
//                            )
//                    );
//                } else {
//                    Address address = this.addressService.createAddress(user.getAddress());
                    this.companyBranchService.createCompanyBranchWithCategoriesAndSubcategories(
                            new CompanyBranchBody(
                                    user.getFantasyName(),
                                    userId.getId(),
                                    user.getAddress(),
                                    user.getCategory(),
                                    user.getSubCategories()
                            )
                    );
//                }

//                CompanyBranch companyCreated =
//                        this.companyBranchService.createCompanyBranch(
//                                new CompanyBranchBody(
//                                        user.getFantasyName(),
//                                        userId.getId(),
//                                        address
//                                )
//                        );
//
//                portfolio = this.portfolioService.createPortfolio(
//                        companyCreated.getId(),
//                        user.getCategory(),
//                        user.getSubCategories());
            } else {
//                Role role = this.roleRepository.findByName("USER");
//                user.addRole(role);
                user.setRole(Role.USER);
                this.userRepository.save(new User(user));
            }

        } catch (Exception e) {
            if (userId.getId() != null ) this.userRepository.delete(userId);
//            if (address != null ) this.addressRepository.delete(address);
            if (portfolio.getId() != null ) this.portfolioRepository.delete(portfolio);

            logger.error("User register unexpected error: " + e.getMessage());
            throw e;
        }
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

        if ( Objects.nonNull(user.getFirstName()) || !(Strings.trimToNull(user.getFirstName()) == null) ) {
            if (user.getFirstName().length() < 2)
                throw new IllegalUserArgumentException("Invalid username length!");

            userToUpdate.setFirstName(user.getFirstName().trim());
        }

        if ( Objects.nonNull(user.getLastName()) || !(Strings.trimToNull(user.getLastName()) == null) )
            userToUpdate.setLastName(user.getLastName().trim());
        else
            throw new IllegalUserArgumentException("Invalid username length!");

        if (Objects.nonNull(user.getPhone()) || !(Strings.trimToNull(user.getPhone()) == null) ) {
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

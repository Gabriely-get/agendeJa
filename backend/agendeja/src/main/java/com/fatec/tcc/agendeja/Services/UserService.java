package com.fatec.tcc.agendeja.Services;

import com.fatec.tcc.agendeja.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendeja.CustomExceptions.UserDoesNotExistsException;
import com.fatec.tcc.agendeja.Entities.Role;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Repositories.RoleRepository;
import com.fatec.tcc.agendeja.Repositories.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public User getUserById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            //if (user.getIsActive())
                return user;
        }

        throw new UserDoesNotExistsException("User does not exists");
    }

    public List<User> getAllUsers() {
        Iterable<User> iterableUsers = this.userRepository.findAll();
        List<User> userList = new ArrayList<>();
        iterableUsers.forEach(userList::add);

        return userList;
    }

    public List<User> getActiveUsers() {
        List<User> users = this.userRepository.findAll();

        return users.stream().filter(User::getIsActive).toList();
    }

    public void createUser(User user) throws SQLIntegrityConstraintViolationException {
        BCryptPasswordEncoder bCryptPasswordEncoder;
        boolean userAlreadyExistsByCpf = this.userRepository.existsUserByCpf(user.getCpf());
        boolean userAlreadyExistsByEmail = this.userRepository.existsUserByEmail(user.getEmail());
        boolean userAlreadyExistsByPhone = this.userRepository.existsUserByPhone(user.getPhone());

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
        System.out.println(this.roleRepository.findByName("USER"));
        Role role = this.roleRepository.findByName("USER");
        user.addRole(role);
        this.userRepository.save(new User(user));

    }

    public void updateUser(Long id, User user) {
        //TODO
        // save user picture too
        // validate date birth day
        //DONE verify if new phone already exists
        //DONE paginação
        //DONE ordenação
        //DONE busca (ex: silva, quem tem silva no nome?)

        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isEmpty()) throw new UserDoesNotExistsException("User does not exists");
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

package com.fatec.tcc.agendeja.Services;

import com.fatec.tcc.agendeja.CustomExceptions.UserDoesNotExistsException;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        throw new UserDoesNotExistsException("User does not exists");
    }

    public List<User> getAllUsers() {
        Iterable<User> iterableUsers = this.userRepository.findAll();
        List<User> userList = new ArrayList<>();
        iterableUsers.forEach(userList::add);

        return userList;
    }

    public void createUser(User user) throws SQLIntegrityConstraintViolationException {
        BCryptPasswordEncoder bCryptPasswordEncoder;
        boolean userAlreadyExistsByCpf = this.userRepository.existsUserByCpf(user.getCpf());
        boolean userAlreadyExistsByEmail = this.userRepository.existsUserByEmail(user.getEmail());

        if (userAlreadyExistsByCpf) {
            throw new SQLIntegrityConstraintViolationException("CPF is already registered");
        }
        if (userAlreadyExistsByEmail) {
            throw new SQLIntegrityConstraintViolationException("E-mail is already registered");
        }

        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String hashedPass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(hashedPass);

        this.userRepository.save(new User(user));

    }

}

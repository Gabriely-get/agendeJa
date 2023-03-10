package com.fatec.tcc.agendeja.Services;

import com.fatec.tcc.agendeja.CustomExceptions.UserDoesNotExistsException;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Repositories.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public String login(String email, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder;

        if (this.userRepository.existsUserByEmail(email)) {
            User user = this.userRepository.findUserByEmail(email).get();

            bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean passMatches = bCryptPasswordEncoder.matches(password, user.getPassword());

            if (passMatches) {
                return user.getUsername();
            }

            throw new UserDoesNotExistsException("Verify fields!");
        }

         throw new UserDoesNotExistsException("User not registered!");

    }

}

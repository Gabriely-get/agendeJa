package com.fatec.tcc.agendeja.Services;

import com.fatec.tcc.agendeja.CustomExceptions.UserDoesNotExistsException;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Long login(String email, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder;

        if (this.userRepository.existsUserByEmail(email)) {
            User user = this.userRepository.findUserByEmail(email).get();

            bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean passMatches = bCryptPasswordEncoder.matches(password, user.getPassword());

            if (passMatches) {
                return user.getId();
            }

            throw new UserDoesNotExistsException("Verify fields!");
        }

         throw new UserDoesNotExistsException("User not registered!");

    }

}

package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.User;
import com.fatec.tcc.agendify.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class                                                   LoginService {

    @Autowired
    private UserRepository userRepository;

//    public Long login(String email, String password) {
//        BCryptPasswordEncoder bCryptPasswordEncoder;
//        Optional<User> optionalUser = this.userRepository.findUserByEmail(email);
//
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            bCryptPasswordEncoder = new BCryptPasswordEncoder();
//
//            if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
//                return user.getId();
//            }
//
//            throw new NotFoundException("Invalid password!");
//        }
//
//         throw new NotFoundException("User not registered!");
//
//    }

}

package com.fatec.tcc.agendeja.Database.Seeders;

import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Component
public class Seed {
    @Autowired
    private UserRepository userRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) throws ParseException {

        this.seedUser(true);
    }

    private void seedUser(boolean active) throws ParseException {
        int qt = this.userRepository.findAll().size();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        if (qt < 6) {
            boolean change = false;

            for (int i = 1; i < 10; i++) {
                if (change) {
                    String email = "teste"+i+"@gmail.com";
                    String cpf = "1234567898" + i;
                    String dateInString = "2002-07-0" + i;

                    active = !active;

                    User user = new User();
                    user.setCpf(cpf);
                    user.setEmail(email);
                    user.setUsername("Gabriely Santosos " + i);
                    user.setIsActive(active);
                    user.setPhone(cpf);
                    user.setBirthday(formatter.parse(dateInString));
                    user.setPassword(new BCryptPasswordEncoder().encode("test123"));

                    this.userRepository.save(user);
                    change = false;
                } else {
                    String email = "gab"+i+"@gmail.com";
                    String cpf = i+"266567898" + i;
                    String dateInString = "200"+i+"-08-0" + i;

//TODO validate if older than 17 years old

                    active = !active;

                    User user = new User();
                    user.setCpf(cpf);
                    user.setEmail(email);
                    user.setUsername("Rodrigo Pens " + i);
                    user.setIsActive(active);
                    user.setPhone(cpf);
                    user.setBirthday(formatter.parse(dateInString));
                    user.setPassword(new BCryptPasswordEncoder().encode("test123"));

                    this.userRepository.save(user);
                    change = true;
                }

            }
        }
    }

}

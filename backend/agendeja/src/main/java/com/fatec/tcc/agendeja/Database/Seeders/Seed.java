package com.fatec.tcc.agendeja.Database.Seeders;

import com.fatec.tcc.agendeja.Entities.Role;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Repositories.RoleRepository;
import com.fatec.tcc.agendeja.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class Seed {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @EventListener
    public void seed(ContextRefreshedEvent event) throws ParseException {

        this.seedRoles();
        this.seedUser(true);
    }

    private void seedRoles() {
        int qt = this.roleRepository.findAll().size();

        if (qt < 1) {
            Role role1 = new Role("USER");
            Role role2 = new Role("ENTERPRISE");
            Role role3 = new Role("ADMIN");

            this.roleRepository.saveAll(List.of(role1, role2, role3));
        }
    }

    @Transactional
    private void seedUser(boolean active) throws ParseException {
        int qt = this.userRepository.findAll().size();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Set<Role> roles = new HashSet<>();
        roles.add(this.roleRepository.findById(1L).get());
        System.out.println(roles);

        if (qt < 6) {
            boolean change = false;

            for (int i = 1; i < 9; i++) {
                if (change) {
                    String email = "teste"+i+"@gmail.com";
                    String cpf = "1234567898" + i;
                    String dateInString = "2002-07-0" + i;

                    active = !active;

                    User user = new User();
                    user.setCpf(cpf);
                    user.setEmail(email);
                    user.setFirstName("Gabriely");
                    user.setLastName(" Santos " + i);
                    user.setIsActive(active);
                    user.setPhone(cpf);
                    user.setBirthday(formatter.parse(dateInString));
                    user.setPassword(new BCryptPasswordEncoder().encode("test123"));
                    user.setRoles(Set.of(this.roleRepository.findById(1L).get()));

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
                    user.setFirstName("Rodrigo");
                    user.setLastName(" Ablu " + i);
                    user.setIsActive(active);
                    user.setPhone(cpf);
                    user.setBirthday(formatter.parse(dateInString));
                    user.setPassword(new BCryptPasswordEncoder().encode("test123"));
                    System.out.println(this.roleRepository.findById(3L).get());
                    user.addRole(this.roleRepository.findById(3L).get());

                    this.userRepository.save(user);
                    change = true;
                }

            }

            for (int i = 1; i < 3; i++) {
                String email = "mandinha"+i+"@gmail.com";
                String cpf = i+"360278988" + i;
                String dateInString = "200"+i+"-04-0" + i;

                User user = new User();
                user.setCpf(cpf);
                user.setEmail(email);
                user.setFirstName("Amanda ");
                user.setLastName(" Suit" + i);
                user.setIsActive(active);
                user.setPhone(cpf);
                user.setBirthday(formatter.parse(dateInString));
                user.setPassword(new BCryptPasswordEncoder().encode("test123"));
                user.setRoles(Set.of(this.roleRepository.findById(2L).get()));

                this.userRepository.save(user);
            }
        }
    }

}

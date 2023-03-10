package com.fatec.tcc.agendeja.Entities;

import com.fatec.tcc.agendeja.Utils.UserFieldsValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
public @Data class User {
    public User(User user) {
        UserFieldsValidation.userPOJOValidation(user);

        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setCpf(user.getCpf());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @NotBlank(message = "Name is mandatory")
    private String username;
    @Size(min = 11, max = 11, message = "CPF is mandatory")
    private String cpf;
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private Set<Role> roles = new HashSet<>();

    public User() {

    }
}

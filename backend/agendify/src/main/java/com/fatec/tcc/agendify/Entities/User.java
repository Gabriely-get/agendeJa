package com.fatec.tcc.agendify.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fatec.tcc.agendify.Entities.RequestTemplate.UserBody;
import com.fatec.tcc.agendify.Utils.UserFieldsValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.*;

@Entity
public @Data class User implements UserDetails {
    public User() {

    }

    public User(User user) {
        UserFieldsValidation.userPOJOValidation(user);

        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setCpf(user.getCpf());
        this.setPhone(user.getPhone());
        this.setBirthday(user.getBirthday());
        this.setIsActive(user.getIsActive());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
//        this.setRoles(user.getRoles());
        this.setRole(user.getRole());
        this.setImageProfileId(user.getImageProfileId());
        this.setIsJobProvider(user.getIsJobProvider());
    }

    public User(UserBody user) {
        UserFieldsValidation.userPOJOValidation(user);

        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setCpf(user.getCpf());
        this.setPhone(user.getPhone());
        this.setBirthday(user.getBirthday().toString());
        this.setIsActive(user.getIsActive());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
//        this.setRoles(user.getRoles());
        this.setRole(user.getRole());
        this.setImageProfileId(user.getImageProfileId());
        this.setImageCoverId(user.getImageCoverId());
        this.setIsJobProvider(user.getIsJobProvider());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    @NotNull
    private String email;
    @Column(nullable = false)
    @NotBlank
    @NotNull
    private String password;
    @NotBlank
    @NotNull
    private String firstName;
    @NotBlank
    @NotNull
    private String lastName;
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull
    private String birthday;
    @NotBlank
    @NotNull
    private String cpf;

    @NotNull
    private String phone;

    private Boolean isActive;
    @NotNull
    private Boolean isJobProvider;
    @CreationTimestamp
    private Timestamp createAt;

    @LastModifiedDate
    private Timestamp updateAt;

    @Enumerated(EnumType.STRING)
    private Role role;
    @Nullable
    private Long imageProfileId;
    @Nullable
    private Long imageCoverId;

    private String getFullUsername() {
        return this.getFirstName().trim() + ' ' + this.getLastName();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    @Override
    //controle de acesso de perfis
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRole().name()));
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    private Collection<? extends GrantedAuthority> getRoles(User user) {
//    List<GrantedAuthority> authorities = new ArrayList<>();
//    for (Role role : user.getRoles()) {
//      authorities.add(new SimpleGrantedAuthority(role.getName()));
//    }
//    return authorities;
//  }

}

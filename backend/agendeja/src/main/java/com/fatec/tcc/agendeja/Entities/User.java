package com.fatec.tcc.agendeja.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fatec.tcc.agendeja.Utils.UserFieldsValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public @Data class User {
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
        this.setRoles(user.getRoles());
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotBlank
    private String email;
    @Column(nullable = false)
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone = "UTC-3")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Size(min = 11, max = 11, message = "CPF is mandatory")
    @NotBlank
    private String cpf;

    @Size(min = 11, max = 11, message = "Phone is mandatory")
    private String phone;

    private Boolean isActive;
    @CreationTimestamp
    private Timestamp createAt;

    @LastModifiedDate
    private Timestamp updateAt;

    @ManyToMany()
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id" ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id" ))
    private Set<Role> roles = new HashSet<>();

    private String getFullUsername() {
        return this.getFirstName().trim() + ' ' + this.getLastName();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

    public  void addRole(Role role) {
        this.roles.add(role);
    }

//    private Collection<? extends GrantedAuthority> getRoles(User user) {
//    List<GrantedAuthority> authorities = new ArrayList<>();
//    for (Role role : user.getRoles()) {
//      authorities.add(new SimpleGrantedAuthority(role.getName()));
//    }
//    return authorities;
//  }

}

package com.fatec.tcc.agendify.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fatec.tcc.agendify.Entities.RequestTemplate.UserBody;
import com.fatec.tcc.agendify.Utils.UserFieldsValidation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
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
//        this.setRoles(user.getRoles());
        this.setRole(user.getRole());
        this.setImageId(user.getImageId());
        this.setIsJobProvider(user.getIsJobProvider());
    }

    public User(UserBody user) {
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
        this.setImageId(user.getImageId());
        this.setIsJobProvider(user.getIsJobProvider());
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
    @NotBlank
    private String cpf;

    private String phone;

    private Boolean isActive;
    private Boolean isJobProvider;
    @CreationTimestamp
    private Timestamp createAt;

    @LastModifiedDate
    private Timestamp updateAt;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_role",
//            joinColumns = @JoinColumn(
//                    name = "user_id" ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "role_id" ))
//    private Set<Role> roles = new HashSet<>();
    private Role role;
    private Long imageId;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
//    private Set<JobCategory> jobCategories;

    private String getFullUsername() {
        return this.getFirstName().trim() + ' ' + this.getLastName();
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName.trim();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
    }

//    public  void addRole(Role role) {
//        this.roles.add(role);
//    }

//    private Collection<? extends GrantedAuthority> getRoles(User user) {
//    List<GrantedAuthority> authorities = new ArrayList<>();
//    for (Role role : user.getRoles()) {
//      authorities.add(new SimpleGrantedAuthority(role.getName()));
//    }
//    return authorities;
//  }

}

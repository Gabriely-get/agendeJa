package com.fatec.tcc.agendeja.Entities.RequestTemplate;

import com.fatec.tcc.agendeja.Entities.RoleType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public @Data class UserBody {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String cpf;
    private String phone;
    private String fantasyName;
    private Boolean hasAddress;
    private Boolean isActive;
    private Boolean isJobProvider;
    private Long category;
    private List<Long> subCategories;
//    private Set<Role> roles = new HashSet<>();
    private RoleType role;
    private CepApi address;
    private Long imageId;
    private MultipartFile file;

//    public  void addRole(Role role) {
//        this.roles.add(role);
//    }

}

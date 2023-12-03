package com.fatec.tcc.agendify.Entities.RequestTemplate;

import com.fatec.tcc.agendify.Entities.Role;
import lombok.Data;

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
    private Role role;
    private CepApi address;
    private Long imageProfileId;
    private Long imageCoverId;
    private String profileImage;
    private String coverImage;
    private List<BussinesHourData> hours;
    private Boolean is24Hours;
    private String description;

}

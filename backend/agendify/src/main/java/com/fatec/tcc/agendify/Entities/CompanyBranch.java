package com.fatec.tcc.agendify.Entities;

import com.fatec.tcc.agendify.Entities.Address.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Entity
@Table(name = "companybranch")
public @Data class CompanyBranch {
    public CompanyBranch() {

    }
    public CompanyBranch(String name, Address address, User user, String description, Boolean is24Hours) {
        this.setName(name);
        this.setUser(user);
        this.setAddress(address);
        this.setDescription(description);
        this.setIs24Hours(is24Hours);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    @Nullable
    private Address address;

    @Nullable
    private String description;

    @NotNull
    private Boolean is24Hours;

    public void setName(String name) {
        if (Strings.trimToNull(name) == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Company name is required");
        }
        this.name = name;
    }

//    public void setAddress(Address address) {
//        if (address != null)
//            this.address = address;
//    }

    public void setUser(User user) {
        if (Objects.isNull(user)) {
            throw new IllegalArgumentException("User is required for company branch");
        }
        this.user = user;
    }

}

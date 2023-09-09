package com.fatec.tcc.agendify.Entities;

import com.fatec.tcc.agendify.Entities.Address.Address;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
@Table(name = "companybranch")
public @Data class CompanyBranch {
    public CompanyBranch() {

    }
    public CompanyBranch(String name, Address address, User user) {
        this.setName(name);
        this.setUser(user);
        this.setAddress(address);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Address address;

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

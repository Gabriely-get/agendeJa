package com.fatec.tcc.agendeja.Entities.Address;

import com.fatec.tcc.agendeja.Entities.CompanyBranch;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;

@Entity
public @Data class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "neighborhood_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Neighborhood neighborhood;
    private String publicPlace;
    private String number;
    private String complement;

    // TODO field update at for verify on front and make an alert on enterprise page about the new address
    @LastModifiedDate
    private Timestamp updateAt;

    public Address(String cep, String logradouro, String number, String complement, Neighborhood neighborhoodObject) {
        this.cep = cep;
        this.publicPlace = logradouro;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhoodObject;
    }

    public Address() {

    }
}

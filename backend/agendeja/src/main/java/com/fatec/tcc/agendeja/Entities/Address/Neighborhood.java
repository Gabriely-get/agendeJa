package com.fatec.tcc.agendeja.Entities.Address;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public @Data class Neighborhood {
    public Neighborhood() {

    }

    public Neighborhood(String name, City city) {
        this.name = name;
        this.city = city;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private City city;
}

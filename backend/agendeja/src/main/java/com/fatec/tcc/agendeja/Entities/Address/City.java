package com.fatec.tcc.agendeja.Entities.Address;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public @Data class City {
    public City() {

    }

    public City(String name, State state) {
        this.name = name;
        this.state = state;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "state_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private State state;

}

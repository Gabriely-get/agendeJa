package com.fatec.tcc.agendeja.Entities.Address;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@AllArgsConstructor
public @Data class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameAbbreviation;

    public State() {

    }

    public State(String nameAbbreviation) {
        this.nameAbbreviation = nameAbbreviation;
    }
}

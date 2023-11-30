package com.fatec.tcc.agendify.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Entity
@Table(name = "category")
public @Data class Category {
    public Category() {

    }

    public Category(String name) {
        this.setName(name);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    public void setName(String name) {
        if (Strings.trimToNull(name) == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Category name is required");
        }
        this.name = name;
    }

}

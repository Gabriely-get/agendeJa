package com.fatec.tcc.agendeja.Entities;

import com.fatec.tcc.agendeja.CustomExceptions.IllegalUserArgumentException;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private String name;

//    fetch = FetchType.EAGER,
//    @OneToMany(mappedBy = "category")
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name = "category_id", referencedColumnName = "id")
//    private Set<SubCategory> subCategories = new HashSet<>();

    public void setName(String name) {
        if (Strings.trimToNull(name) == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Category name is required");
        }
        this.name = name;
    }

}

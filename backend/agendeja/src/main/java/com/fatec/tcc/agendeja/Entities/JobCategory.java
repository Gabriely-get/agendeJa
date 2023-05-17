package com.fatec.tcc.agendeja.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity
public @Data class JobCategory {

    public JobCategory() {

    }

    public JobCategory(String name, SubCategory subCategory) {
        this.setName(name);
        this.setSubCategory(subCategory);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "subcategory_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SubCategory subCategory;

    public void setName(String name) {
        if (Strings.trimToNull(name) == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Job name is required");
        }
        this.name = name;
    }

    public void setSubCategory(SubCategory subCategory) {
        if (Objects.isNull(subCategory)) {
            throw new IllegalArgumentException("Sub category name is required");
        }
        this.subCategory = subCategory;
    }

}

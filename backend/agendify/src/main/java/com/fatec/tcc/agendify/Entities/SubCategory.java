package com.fatec.tcc.agendify.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;

@Entity()
@Table(name = "subcategory")
public @Data class SubCategory {
    public SubCategory() {

    }
    public SubCategory(String name, Category category) {
        this.setName(name);
        this.setCategory(category);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull
    private Category category;

    public void setName(String name) {
        if (Strings.trimToNull(name) == null || name.isEmpty() || name.isBlank()) {
            throw new IllegalArgumentException("Sub category name is required");
        }
        this.name = name;
    }

    public void setCategory(Category category) {
        if (Objects.isNull(category)) {
            throw new IllegalArgumentException("Category name is required");
        }
        this.category = category;
    }



}

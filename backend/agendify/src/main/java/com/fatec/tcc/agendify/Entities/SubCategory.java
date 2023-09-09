package com.fatec.tcc.agendify.Entities;

import jakarta.persistence.*;
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
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "subCategories")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Set<Portfolio> portfolios;

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "subCategory")
//    private Set<Portfolio> portfolios;

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

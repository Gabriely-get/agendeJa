package com.fatec.tcc.agendify.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public @Data class Portfolio {
    public Portfolio() {

    }
    public Portfolio(CompanyBranch companyBranch, Category category, Set<SubCategory> subCategories) {
        this.setCompanyBranch(companyBranch);
        this.setSubCategories(subCategories);
        this.setCategory(category);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "companybranch_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CompanyBranch companyBranch;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "portfolio_subcategory",
            joinColumns = @JoinColumn(
                    name = "portfolio_id" ),
            inverseJoinColumns = @JoinColumn(
                    name = "subcategory_id" ))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<SubCategory> subCategories = new HashSet<>();

//    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "portfolio")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Set<PortfolioJob> portfolioJobs;

    public void setCompanyBranch(CompanyBranch companyBranch) {
        if (Objects.isNull(companyBranch)) {
            throw new IllegalArgumentException("Company is required for portfolio");
        }
        this.companyBranch = companyBranch;
    }

    public void setCategory(Category category) {
        if (Objects.isNull(category)) {
            throw new IllegalArgumentException("Category is required for portfolio");
        }
        this.category = category;
    }

    public void setSubCategories(Set<SubCategory> subCategories) {
        if (Objects.isNull(subCategories)) {
            throw new IllegalArgumentException("Sub Category is required for portfolio");
        }
        this.subCategories = subCategories;
    }

}

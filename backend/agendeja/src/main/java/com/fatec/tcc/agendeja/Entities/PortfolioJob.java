package com.fatec.tcc.agendeja.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Objects;
import java.util.Set;

@Entity
public @Data class PortfolioJob {
    public PortfolioJob() {
    }

    public PortfolioJob(String name, Double price, String description, Portfolio portfolio, JobCategory jobCategory) {
        this.setName(name);
        this.setPrice(price);
        this.setDescription(description);
        this.setPortfolio(portfolio);
        this.setJobCategory(jobCategory);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "job_category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JsonIgnore
    private JobCategory jobCategory;

    public void setPortfolio(Portfolio portfolio) {
        if (Objects.isNull(portfolio)) {
            throw new IllegalArgumentException("Portfolio job is required");
        }
        this.portfolio = portfolio;
    }

    public void setJobCategory(JobCategory jobCategory) {
        if (Objects.isNull(jobCategory)) {
            throw new IllegalArgumentException("Job portfolio name is required");
        }
        this.jobCategory = jobCategory;
    }


}

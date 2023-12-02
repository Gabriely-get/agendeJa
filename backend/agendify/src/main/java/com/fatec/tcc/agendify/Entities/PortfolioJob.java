package com.fatec.tcc.agendify.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.Nullable;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

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
    @NotNull
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private String description;

    @Nullable
    private Long imageCoverId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @NotNull
    private Portfolio portfolio;

    @OneToMany(mappedBy = "portfolioJob", fetch = FetchType.EAGER)
    private List<Image> images;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "job_category_id", nullable = false)
    @NotNull
    private JobCategory jobCategory;
    private LocalTime duration;
    private Boolean restricted;

    public PortfolioJob(String name, Double price, String description, Long imageCover, Portfolio portfolio, JobCategory jobCategory, LocalTime duration, Boolean restricted) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageCoverId = imageCover;
        this.portfolio = portfolio;
        this.jobCategory = jobCategory;
        this.duration = duration;
        this.restricted = restricted;
    }

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

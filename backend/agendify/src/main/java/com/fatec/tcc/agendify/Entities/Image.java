package com.fatec.tcc.agendify.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

@Table
@Entity
@Data
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16777215, columnDefinition = "mediumtext")
    private String base64;

    @Column
    private Date createdAt;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "portfolio_job_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PortfolioJob portfolioJob;

    public Image(String base64, Date date) {
        this.base64 = base64;
        this.createdAt = date;
    }

    public Image() {

    }

    public Image(String img, Date date, PortfolioJob portfolioJob) {
        this.base64 = img;
        this.createdAt = date;
        this.portfolioJob = portfolioJob;
    }
}
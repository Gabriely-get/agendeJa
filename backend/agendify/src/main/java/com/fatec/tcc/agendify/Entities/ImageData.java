package com.fatec.tcc.agendify.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "imageData")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Column(name = "imagedata")
    private byte[] imageData;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "portfolio_job_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PortfolioJob portfolioJob;

    //nem testei essa fita
//    @OneToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "user_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private User user;

}
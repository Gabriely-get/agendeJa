package com.fatec.tcc.agendeja.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Entity
public @Data class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private String dateAsString;

    @JsonDeserialize(using = LocalTimeDeserializer.class)
    @JsonSerialize(using = LocalTimeSerializer.class)
    private LocalTime time;
    private String timeAsString;
    private Boolean isScheduled;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "portfolio_job_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PortfolioJob portfolioJob;

//    @ManyToOne(fetch = FetchType.EAGER, optional = false)
//    @JoinColumn(name = "user_id")
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private Long userId;

    public Schedule(LocalDate date, LocalTime time, PortfolioJob portfolioJob) {
        this.setDate(date);
        this.setTime(time);
        this.setPortfolioJob(portfolioJob);
    }

    public Schedule() {

    }

    public void setDate(LocalDate date) {
        if (Objects.isNull(date) || date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid date for schedule");
        }
        this.date = date;
    }

    public void setTime(LocalTime time) {
        if (Objects.isNull(time)) {
            throw new IllegalArgumentException("Invalid time for schedule");
        }
        this.time = time;
    }

    public void setPortfolioJob(PortfolioJob portfolioJob1) {
        if (Objects.isNull(portfolioJob1)) {
            throw new IllegalArgumentException("Invalid portfolio job for schedule");
        }
        this.portfolioJob = portfolioJob1;
    }

}

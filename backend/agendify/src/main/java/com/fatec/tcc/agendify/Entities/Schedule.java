package com.fatec.tcc.agendify.Entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.jetbrains.annotations.Nullable;

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
//    @NotNull
//    private Boolean isScheduled;

    @NotNull
    private String status;

    @Nullable
    private String noteByDecline;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "portfolio_job_id", nullable = false)
    @NotNull
    private PortfolioJob portfolioJob;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public Schedule(LocalDate date, LocalTime time, PortfolioJob portfolioJob) {
        this.setDate(date);
        this.setTime(time);
        this.setPortfolioJob(portfolioJob);
    }

    public Schedule() {

    }

    public Schedule(LocalDate date, LocalTime time, PortfolioJob portfolioJob, User user, SCHEDULE_STATUS pendente) {
        this.setDate(date);
        this.setTime(time);
        this.setPortfolioJob(portfolioJob);
        this.user = user;
        this.status = pendente.name();
    }

    public Schedule(
            LocalDate date,
            LocalTime time,
            PortfolioJob portfolioJob,
            User user,
            SCHEDULE_STATUS pendente,
            Boolean isScheduled,
            String noteByDecline
    ) {
        this.setDate(date);
        this.setTime(time);
        this.setPortfolioJob(portfolioJob);
        this.user = user;
        this.status = pendente.name();
//        this.isScheduled = isScheduled;
        this.noteByDecline = noteByDecline;
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

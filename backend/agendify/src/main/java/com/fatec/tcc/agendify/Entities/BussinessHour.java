package com.fatec.tcc.agendify.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Data
public class BussinessHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    DaysOfWeek dayOfWeek;
    @NotNull
    LocalTime startTime;
    @NotNull
    LocalTime endTime;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @NotNull
    private Portfolio portfolio;

    public BussinessHour(DaysOfWeek dayOfWeek, LocalTime start, LocalTime end, Portfolio portfolio) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = start;
        this.endTime = end;
        this.portfolio = portfolio;
    }

    public BussinessHour() {

    }
}

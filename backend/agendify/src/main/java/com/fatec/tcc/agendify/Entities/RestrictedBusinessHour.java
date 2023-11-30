package com.fatec.tcc.agendify.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class RestrictedBusinessHour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Future
    Date date;

    @ElementCollection
    private List<LocalTime> hour;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

}

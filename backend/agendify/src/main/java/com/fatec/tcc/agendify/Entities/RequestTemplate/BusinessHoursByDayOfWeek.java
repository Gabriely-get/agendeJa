package com.fatec.tcc.agendify.Entities.RequestTemplate;

import com.fatec.tcc.agendify.Entities.DaysOfWeek;

import java.time.LocalDate;

public record BusinessHoursByDayOfWeek(Long portfolioId, LocalDate date) {
}

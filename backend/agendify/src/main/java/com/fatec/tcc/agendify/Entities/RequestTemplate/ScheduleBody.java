package com.fatec.tcc.agendify.Entities.RequestTemplate;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleBody(
        Long portfolioJobId,
        LocalDate date,
        LocalTime time,
        Long userId
) {
}

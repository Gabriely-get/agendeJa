package com.fatec.tcc.agendify.Entities.RequestTemplate;

import java.sql.Time;
import java.time.LocalTime;

public record BussinesHourData(
        String dayOfWeek,
        LocalTime start,
        LocalTime end
) {
}

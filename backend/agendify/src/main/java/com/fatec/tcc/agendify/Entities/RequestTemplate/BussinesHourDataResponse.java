package com.fatec.tcc.agendify.Entities.RequestTemplate;

import java.time.LocalTime;

public record BussinesHourDataResponse(
        String dayOfWeek,
        String startTime,
        String endTime
) {
}

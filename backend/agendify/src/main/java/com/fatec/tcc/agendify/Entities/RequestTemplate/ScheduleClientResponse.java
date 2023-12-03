package com.fatec.tcc.agendify.Entities.RequestTemplate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fatec.tcc.agendify.Entities.SCHEDULE_STATUS;
import com.fatec.tcc.agendify.Entities.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleClientResponse(
        Long id,
        String companyName,
        String jobName,
        String duration,
        String date,
        String appointment,
        String status
) {
    public ScheduleClientResponse(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getPortfolioJob().getPortfolio().getCompanyBranch().getName(),
                schedule.getPortfolioJob().getName(),
                schedule.getPortfolioJob().getDuration() == null ? null : schedule.getPortfolioJob().getDuration().toString().toString(),
                schedule.getDate().toString(),
                schedule.getTime().toString(),
                schedule.getStatus()
        );
    }
}

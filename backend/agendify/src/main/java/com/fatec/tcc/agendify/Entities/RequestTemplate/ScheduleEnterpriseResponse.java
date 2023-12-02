package com.fatec.tcc.agendify.Entities.RequestTemplate;

import com.fatec.tcc.agendify.Entities.SCHEDULE_STATUS;
import com.fatec.tcc.agendify.Entities.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;

public record ScheduleEnterpriseResponse(
        Long id,
        String userName,
        String jobName,
        String date,
        String appointment,
        String status
) {
    public ScheduleEnterpriseResponse(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getUser().getFirstName()+' '+schedule.getUser().getLastName(),
                schedule.getPortfolioJob().getName(),
                schedule.getDate().toString(),
                schedule.getTime().toString(),
                schedule.getStatus()
        );
    }
}

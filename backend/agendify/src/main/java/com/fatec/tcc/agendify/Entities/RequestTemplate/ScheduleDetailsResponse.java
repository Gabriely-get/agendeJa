package com.fatec.tcc.agendify.Entities.RequestTemplate;

import com.fatec.tcc.agendify.Entities.Schedule;

import java.time.LocalTime;

public record ScheduleDetailsResponse(
        Long id,
        String companyName,
        String jobName,
        String clientName,
        String duration,
        String date,
        String appointment,
        String status
) {
    public ScheduleDetailsResponse(Schedule schedule) {
        this(
                schedule.getId(),
                schedule.getPortfolioJob().getPortfolio().getCompanyBranch().getName(),
                schedule.getPortfolioJob().getName(),
                schedule.getUser().getFirstName()+' '+schedule.getUser().getLastName(),
                schedule.getPortfolioJob().getDuration() == null ? null : schedule.getPortfolioJob().getDuration().toString(),
                schedule.getDate().toString(),
                schedule.getTime().toString(),
                schedule.getStatus()
        );
    }
}

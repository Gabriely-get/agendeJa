package com.fatec.tcc.agendify.Entities.RequestTemplate;

public record ManageStatusAcceptAppointment(
        Long scheduleId,
        Boolean accept,
        String note) {
}

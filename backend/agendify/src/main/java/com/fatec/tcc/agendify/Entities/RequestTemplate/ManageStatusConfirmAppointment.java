package com.fatec.tcc.agendify.Entities.RequestTemplate;

public record ManageStatusConfirmAppointment(
        Long scheduleId,
        Boolean confirm,
        Boolean reschedule,
        String newDate,
        String newTime,
        String note) {
}

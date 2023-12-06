package com.fatec.tcc.agendify.Entities.RequestTemplate;

public record ManageStatusRelizedAppointment(
        Long scheduleId,
        Boolean realized,
        Boolean reschedule,
        String newDate,
        String newTime,
        String note) {
}

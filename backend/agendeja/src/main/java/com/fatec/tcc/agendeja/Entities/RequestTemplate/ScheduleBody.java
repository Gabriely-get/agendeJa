package com.fatec.tcc.agendeja.Entities.RequestTemplate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public @Data class ScheduleBody {
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String dateAsString;
    private LocalTime time;
    private Long portfolioJobId;
    private Long scheduleId;
    private Long userId;
    //private Boolean idScheduled;
}

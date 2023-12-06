package com.fatec.tcc.agendify.Utils;

import com.fatec.tcc.agendify.Entities.RequestTemplate.Hour;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class GetHoursBetween {

    public static List<Hour> hoursBetween(LocalTime inicio, LocalTime fim) {
        List<Hour> listaHoras = new ArrayList<>();

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de fim");
        }

        LocalTime horaAtual = inicio;

        while (!horaAtual.isAfter(fim)) {
            listaHoras.add(new com.fatec.tcc.agendify.Entities.RequestTemplate.Hour(horaAtual.toString(), true));
            horaAtual = horaAtual.plusHours(1); // Adiciona uma hora
        }

        return listaHoras;
    }

    public static List<String> hoursBetweenAsString(LocalTime inicio, LocalTime fim) {
        List<String> listaHoras = new ArrayList<>();

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de fim");
        }

        LocalTime horaAtual = inicio;

        while (!horaAtual.isAfter(fim)) {
            listaHoras.add(horaAtual.toString());
            horaAtual = horaAtual.plusHours(1); // Adiciona uma hora
        }

        return listaHoras;
    }

    public static List<LocalTime> hoursBetweenAsLocalTime(LocalTime inicio, LocalTime fim) {
        List<LocalTime> listaHoras = new ArrayList<>();

        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("O horário de início deve ser anterior ao horário de fim");
        }

        LocalTime horaAtual = inicio;

        while (!horaAtual.isAfter(fim)) {
            listaHoras.add(horaAtual);
            horaAtual = horaAtual.plusHours(1); // Adiciona uma hora
        }

        return listaHoras;
    }
}

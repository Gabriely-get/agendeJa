package com.fatec.tcc.agendify.Utils;

import com.fatec.tcc.agendify.Entities.RequestTemplate.Hour;

import java.util.Comparator;

public class HourComparator implements Comparator<Hour> {
    @Override
    public int compare(Hour hour1, Hour hour2) {
        // Comparar com base na propriedade LocalTime
        return hour1.time().compareTo(hour2.time());
    }
}

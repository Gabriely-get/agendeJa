package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.Entities.*;
import com.fatec.tcc.agendify.Entities.RequestTemplate.BusinessHoursByDayOfWeek;
import com.fatec.tcc.agendify.Entities.RequestTemplate.BussinesHourData;
import com.fatec.tcc.agendify.Entities.RequestTemplate.Hour;
import com.fatec.tcc.agendify.Repositories.BusinessHourRepository;
import com.fatec.tcc.agendify.Repositories.PortfolioJobRepository;
import com.fatec.tcc.agendify.Repositories.ScheduleRepository;
import com.fatec.tcc.agendify.Utils.HourComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessHourService {
    @Autowired
    private BusinessHourRepository businessHourRepository;

    @Autowired
    private PortfolioJobRepository portfolioJobRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public void register(Boolean is24Hours, List<BussinesHourData> hours, Portfolio portfolio) {

        for (BussinesHourData bhd : hours) {
            if (is24Hours) {
                this.businessHourRepository.save(
                        new BussinessHour(
                                DaysOfWeek.valueOf(bhd.dayOfWeek()),
                                LocalTime.of(0,0),
                                LocalTime.of(23,0),
                                portfolio
                        )
                );
            } else {

                if (bhd.start() != LocalTime.of(0,0) && bhd.end() != LocalTime.of(0,0)) {
                    this.businessHourRepository.save(
                            new BussinessHour(
                                    DaysOfWeek.valueOf(bhd.dayOfWeek()),
                                    bhd.start(),
                                    bhd.end(),
                                    portfolio
                            )
                    );
                }
            }
        }
    }

    public List<String> getBusinessHoursByDay(BusinessHoursByDayOfWeek data) {
//        System.out.println(data);
        Optional<PortfolioJob> optionalPortfolioJob = this.portfolioJobRepository.findById(data.portfolioJobId());
        if (optionalPortfolioJob.isEmpty()) throw new RuntimeException("Serviço não existe");

        LocalDate date = LocalDate.parse(data.date());
        if (!date.isBefore(LocalDate.now())) {
            PortfolioJob job = optionalPortfolioJob.get();

            Optional<BussinessHour> optionalBusinessHour =
                    this.businessHourRepository.findByPortfolio_IdAndDayOfWeek(
                            job.getPortfolio().getId(),
                            DaysOfWeek.valueOf(date.getDayOfWeek().name())
                    );

            if (optionalBusinessHour.isEmpty()) return new ArrayList<>();
            BussinessHour businessHour = optionalBusinessHour.get();

            return this.hoursBetweenAsString(businessHour.getStartTime(), businessHour.getEndTime());
        }
        throw new RuntimeException("Datas anteriores a hoje não possuem horários para agendamento.");
    }

    public List<Hour> getFilteredBusinessHoursByDay(BusinessHoursByDayOfWeek data) {

        try {
            Optional<PortfolioJob> optionalPortfolioJob = this.portfolioJobRepository.findById(data.portfolioJobId());
            if (optionalPortfolioJob.isEmpty()) throw new RuntimeException("Serviço não existe");

            LocalDate date = LocalDate.parse(data.date());
            if (!date.isBefore(LocalDate.now())) {
                PortfolioJob job = optionalPortfolioJob.get();
                Optional<BussinessHour> optionalBusinessHour =
                        this.businessHourRepository.findByPortfolio_IdAndDayOfWeek(
                                job.getPortfolio().getId(),
                                DaysOfWeek.valueOf(date.getDayOfWeek().name())
                        );

                //retorno vazio se o prestador nao possui horarios para aquele dia da semana
                if (optionalBusinessHour.isEmpty()) return new ArrayList<>();

                BussinessHour businessHour = optionalBusinessHour.get();
                List<Hour> hoursFiltered = new ArrayList<>();
                List<LocalTime> listOfHourScheduled = new ArrayList<>();
                List<LocalTime> allHours =
                        this.hoursBetweenAsLocalTime(businessHour.getStartTime(), businessHour.getEndTime());
                List<Schedule> allHoursScheduledByDay =
                        this.scheduleRepository
                                .findAllByPortfolioJob_IdAndDateAndStatusIsNotContaining(
                                        job.getId(),
                                        date,
                                        SCHEDULE_STATUS.CANCELADO.name()
                                );

                for (Schedule schedule:
                        allHoursScheduledByDay) {
                    if (allHours.contains(schedule.getTime())) {
                        hoursFiltered.add(new Hour(schedule.getTime().toString(), false));
                        listOfHourScheduled.add(schedule.getTime());
                    }
                }

                allHours.removeAll(listOfHourScheduled);
                allHours.forEach(h -> hoursFiltered.add(new Hour(h.toString(), true)));
                hoursFiltered.sort(new HourComparator());

                return hoursFiltered;
            }
            throw new RuntimeException("Datas anteriores a hoje não possuem horários para agendamento.");
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private List<Hour> hoursBetween(LocalTime inicio, LocalTime fim) {
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

    private List<String> hoursBetweenAsString(LocalTime inicio, LocalTime fim) {
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

    private List<LocalTime> hoursBetweenAsLocalTime(LocalTime inicio, LocalTime fim) {
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

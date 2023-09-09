package com.fatec.tcc.agendify.Repositories;

import com.fatec.tcc.agendify.Entities.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findAllByUserId(Long id);
//    List<Schedule> findAllByUserId(Long id);

    boolean existsByDateAndTimeAndPortfolioJob_Id(LocalDate date, LocalTime time, Long portfolioJobId);
    boolean existsByDateAndTime(LocalDate date, LocalTime time);
    boolean existsByDateAndTimeAndUserIdAndIsScheduledIsTrue(LocalDate date, LocalTime time, Long userId);

//    List<Schedule> findAllByDateAndTime(LocalDate date, LocalTime time);
}

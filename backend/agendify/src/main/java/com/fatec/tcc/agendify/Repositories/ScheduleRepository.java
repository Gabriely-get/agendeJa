package com.fatec.tcc.agendify.Repositories;

import com.fatec.tcc.agendify.Entities.SCHEDULE_STATUS;
import com.fatec.tcc.agendify.Entities.Schedule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {
    List<Schedule> findAllByUserId(Long id);
    List<Schedule> findAllByPortfolioJob_Portfolio_Id(Long id);
//    List<Schedule> findAllByUserId(Long id);

    boolean existsByDateAndTimeAndPortfolioJob_Id(LocalDate date, LocalTime time, Long portfolioJobId);
    boolean existsByDateAndTimeAndPortfolioJob_Portfolio_Id(LocalDate date, LocalTime time, Long portfolioId);
    boolean findByDateAndTimeAndPortfolioJob_Portfolio_Id(LocalDate date, LocalTime time, Long portfolioId);
//    boolean existsByDateAndTimeAndUserIdAndIsScheduledIsTrue(LocalDate date, LocalTime time, Long userId);
    boolean existsByIdAndPortfolioJob_Portfolio_CompanyBranch_User_Id(Long scheduleId, Long enterpriseUserId);
    List<Schedule> findAllByPortfolioJob_IdAndDateAndStatusIsNotContaining(
            Long portfolioJob_id, LocalDate date, String status
    );

    List<Schedule> findAllByUserIdAndStatusIsContainingIgnoreCase(Long id, String status);

//    List<Schedule> findAllByDateAndTime(LocalDate date, LocalTime time);
}

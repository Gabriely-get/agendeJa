package com.fatec.tcc.agendify.Repositories;

import com.fatec.tcc.agendify.Entities.BusinessHour;
import com.fatec.tcc.agendify.Entities.DaysOfWeek;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusinessHourRepository extends CrudRepository<BusinessHour, Long> {
    Optional<BusinessHour> findByPortfolio_IdAndDayOfWeek(Long id, DaysOfWeek day);
}

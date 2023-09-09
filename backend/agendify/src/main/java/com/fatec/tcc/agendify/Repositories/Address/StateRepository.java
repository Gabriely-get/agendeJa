package com.fatec.tcc.agendify.Repositories.Address;

import com.fatec.tcc.agendify.Entities.Address.State;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CrudRepository<State, Long> {
    boolean existsByNameAbbreviation(String name);
    State findByNameAbbreviation(String name);
}

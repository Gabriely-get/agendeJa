package com.fatec.tcc.agendeja.Repositories.Address;

import com.fatec.tcc.agendeja.Entities.Address.State;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CrudRepository<State, Long> {
    boolean existsByNameAbbreviation(String name);
    State findByNameAbbreviation(String name);
}

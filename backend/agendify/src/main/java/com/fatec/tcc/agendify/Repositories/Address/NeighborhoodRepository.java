package com.fatec.tcc.agendify.Repositories.Address;

import com.fatec.tcc.agendify.Entities.Address.Neighborhood;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeighborhoodRepository extends CrudRepository<Neighborhood, Long> {
    boolean existsByName(String name);
    Neighborhood findByName(String name);
}

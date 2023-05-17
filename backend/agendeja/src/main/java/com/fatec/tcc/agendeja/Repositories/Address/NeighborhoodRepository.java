package com.fatec.tcc.agendeja.Repositories.Address;

import com.fatec.tcc.agendeja.Entities.Address.Neighborhood;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeighborhoodRepository extends CrudRepository<Neighborhood, Long> {
    boolean existsByName(String name);
    Neighborhood findByName(String name);
}

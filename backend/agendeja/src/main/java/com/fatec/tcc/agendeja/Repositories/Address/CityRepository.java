package com.fatec.tcc.agendeja.Repositories.Address;

import com.fatec.tcc.agendeja.Entities.Address.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    boolean existsByName(String name);
    City findByName(String name);
}

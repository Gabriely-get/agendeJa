package com.fatec.tcc.agendify.Repositories.Address;

import com.fatec.tcc.agendify.Entities.Address.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {
    boolean existsByName(String name);
    City findByName(String name);
}

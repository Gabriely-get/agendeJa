package com.fatec.tcc.agendify.Repositories;

import com.fatec.tcc.agendify.Entities.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);
    boolean existsById(Long id);
}

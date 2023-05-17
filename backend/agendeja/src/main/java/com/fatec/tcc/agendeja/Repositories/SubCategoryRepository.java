package com.fatec.tcc.agendeja.Repositories;

import com.fatec.tcc.agendeja.Entities.SubCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends CrudRepository<SubCategory, Long> {
    Optional<SubCategory> findByName(String name);
    boolean existsById(Long id);
//    Optional<SubCategory> get
    List<SubCategory> getAllByCategory_Id(Long id);
}

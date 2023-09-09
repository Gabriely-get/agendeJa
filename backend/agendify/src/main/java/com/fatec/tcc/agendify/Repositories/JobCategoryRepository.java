package com.fatec.tcc.agendify.Repositories;

import com.fatec.tcc.agendify.Entities.JobCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobCategoryRepository extends CrudRepository<JobCategory, Long> {
    Optional<JobCategory> findByName(String name);
    boolean existsById(Long id);
    List<JobCategory> findAllBySubCategory_Id(Long id);
}

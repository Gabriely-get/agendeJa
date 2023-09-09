package com.fatec.tcc.agendify.Repositories;

import com.fatec.tcc.agendify.Entities.CompanyBranch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyBranchRepository extends CrudRepository<CompanyBranch, Long> {
    List<CompanyBranch> findByName(String name);
    boolean existsById(Long id);
    List<CompanyBranch> findAllByUser_Id(Long id);
    int countAllByAddress_Id(Long id);
//    boolean existsByNameAndUser_Id(String name, Long userId);
}

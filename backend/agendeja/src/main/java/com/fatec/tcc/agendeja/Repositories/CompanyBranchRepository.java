package com.fatec.tcc.agendeja.Repositories;

import com.fatec.tcc.agendeja.Entities.CompanyBranch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyBranchRepository extends CrudRepository<CompanyBranch, Long> {
    List<CompanyBranch> findByName(String name);
    boolean existsById(Long id);
    List<CompanyBranch> findAllByUser_Id(Long id);
    int countAllByAddress_Id(Long id);
}

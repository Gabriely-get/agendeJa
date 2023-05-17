package com.fatec.tcc.agendeja.Repositories;

import com.fatec.tcc.agendeja.Entities.Portfolio;
import com.fatec.tcc.agendeja.Entities.SubCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {
    boolean existsByCategory_IdAndCompanyBranch_Id(Long categoryId, Long companyBranchId);
    List<Portfolio> findAllByCompanyBranch_User_Id(Long id);

    boolean existsPortfolioByCompanyBranch_User_Id(Long id);
    boolean existsPortfolioByIdAndCompanyBranch_User_Id(Long portfolioId, Long userId);
//    List<SubCategory> findAllBy

}

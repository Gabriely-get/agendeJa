package com.fatec.tcc.agendify.Repositories;

import com.fatec.tcc.agendify.Entities.PortfolioJob;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioJobRepository extends CrudRepository<PortfolioJob, Long> {
    boolean existsByJobCategory_IdAndPortfolio_IdAndPrice(Long jobId, Long portfolioId, Double price);

//    @Query("Select j, i from PortfolioJob j join ImageData i where i.portfolioJob.id = :id")

    PortfolioJob findPortfolioJobCompleteById(Long id);

    List<PortfolioJob> findAllByPortfolio_CompanyBranch_User_Id(Long id);
    List<PortfolioJob> findAllByPortfolio_CompanyBranch_User_IdAndJobCategory_SubCategory_Id(Long userId, Long subCatId);
}

package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.Image;
import com.fatec.tcc.agendify.Entities.JobCategory;
import com.fatec.tcc.agendify.Entities.Portfolio;
import com.fatec.tcc.agendify.Entities.RequestTemplate.PortfolioJobBody;
import com.fatec.tcc.agendify.Repositories.JobCategoryRepository;
import com.fatec.tcc.agendify.Repositories.PortfolioJobRepository;
import com.fatec.tcc.agendify.Repositories.PortfolioRepository;
import com.fatec.tcc.agendify.Entities.PortfolioJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioJobService {
    Logger logger = LoggerFactory.getLogger(PortfolioJobService.class);
    @Autowired
    private PortfolioJobRepository portfolioJobRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private ImageService imageService;

//    @Autowired
//    private ImageDataService imageDataService;

//    public List<PortfolioJob> getAll(){
//        return (List<PortfolioJob>) this.portfolioJobRepository.findAll();
//    }
//
//    public PortfolioJob getById(Long id) {
//        Optional<PortfolioJob> optionalPortfolioJob = this.portfolioJobRepository.findById(id);
//
//        if (optionalPortfolioJob.isPresent()) {
//            return optionalPortfolioJob.get();
//        }
//
//        throw new NotFoundException("Portfolio does not exists");
//    }

    public PortfolioJob createPortfolioJob(PortfolioJobBody portfolioJobBody) {
        //TODO add desc character limit 100
        try {
            Optional<Portfolio> optionalPortfolio = this.portfolioRepository.findById(portfolioJobBody.portfolioId());
            Optional<JobCategory> optionalJob = this.jobCategoryRepository.findById(portfolioJobBody.jobId());

            if (optionalPortfolio.isEmpty()) throw new IllegalArgumentException("Invalid portfolio for portfolio job!");
            if (optionalJob.isEmpty()) throw new IllegalArgumentException("Invalid job for portfolio job!");

            Portfolio portfolio = optionalPortfolio.get();
            JobCategory jobCategory = optionalJob.get();

//        if (Objects.nonNull(portfolioJobBody.getFile()))
//            this.imageDataService.uploadImage(portfolioJobBody.getFile(), job.getId());

            if (!Objects.equals(portfolio.getCategory().getId(), jobCategory.getSubCategory().getCategory().getId()))
                throw new IllegalArgumentException("An error occurred! The job selected does not belong to the portfolio category!");

            String name = portfolioJobBody.name() == null || portfolioJobBody.name().isEmpty()
                    ? jobCategory.getName()
                    : portfolioJobBody.name();

            if (portfolioJobBody.price() <= 0)
                throw new IllegalArgumentException("An error occurred! Invalid job price!");

            List<Long> imageProfile = new ArrayList<>();
            Image imageCover = null;

            if (portfolioJobBody.coverImage().isEmpty() || portfolioJobBody.coverImage().isBlank()) {
                imageCover = new Image();
            } else {
                imageCover = this.imageService.saveImage(portfolioJobBody.coverImage());
            }

            PortfolioJob newPortfolioJob = this.portfolioJobRepository.save(new PortfolioJob(
                    name,
                    portfolioJobBody.price(),
                    portfolioJobBody.description(),
                    imageCover,
                    portfolio,
                    jobCategory));

            System.out.println(newPortfolioJob.getId());
            if (Objects.nonNull(portfolioJobBody.serviceImages())) {
                portfolioJobBody
                        .serviceImages()
                        .forEach(img -> this.imageService.saveImage(img, newPortfolioJob));
            }

            return this.portfolioJobRepository.save(newPortfolioJob);

        } catch (Exception e) {
            logger.error("Error on create portfolio job: " + e.getMessage());
            throw e;
        }
//        throw new IllegalArgumentException("Job name is already registered");
    }

    public void updatePortfolioJob(Long portfolioJobId, PortfolioJob newPortfolioJob) {
        try {
            Optional<PortfolioJob> optionalPortfolioJob = this.portfolioJobRepository.findById(portfolioJobId);

            if (optionalPortfolioJob.isEmpty()) throw new NotFoundException("Invalid portfolio job! Not found");

            PortfolioJob updatedPortfolioJob = optionalPortfolioJob.get();

//        if (Objects.nonNull(portfolioJobBody.getFile()))
//            this.imageDataService.uploadImage(portfolioJobBody.getFile(), job.getId());

            String name = newPortfolioJob.getName().isEmpty()
                    ? updatedPortfolioJob.getJobCategory().getName()
                    : newPortfolioJob.getName();

            updatedPortfolioJob.setName(name);
            updatedPortfolioJob.setPrice(newPortfolioJob.getPrice());
            updatedPortfolioJob.setDescription(newPortfolioJob.getDescription());

            this.portfolioJobRepository.save(updatedPortfolioJob);
        } catch (Exception e) {
            logger.error("Error on update portfolio job: " + e.getMessage());
            throw e;
        }
//        throw new IllegalArgumentException("Job name is already registered");
    }

    public List<PortfolioJob> getAll(){
        return (List<PortfolioJob>) this.portfolioJobRepository.findAll();
    }

//    public List<PortfolioJob> getAllFormatted(){
//        List<PortfolioJob> portfolioJobList = this.portfolioJobRepository.findAll();
//        List<PortfolioJob> portfolioJobListToSend = new ArrayList<>();
//
//
//
//    }

    public List<PortfolioJob> getAllByUserId(Long id){
        return this.portfolioJobRepository.findAllByPortfolio_CompanyBranch_User_Id(id);
    }

    public List<PortfolioJob> getAllByUserIdAndSubCategoryId(Long userId, Long subCatId){
        return this.portfolioJobRepository
                .findAllByPortfolio_CompanyBranch_User_IdAndJobCategory_SubCategory_Id(userId, subCatId);
    }

    public PortfolioJob getById(Long id) {
        Optional<PortfolioJob> optionalPortfolioJob = this.portfolioJobRepository.findById(id);

        if (optionalPortfolioJob.isPresent()) {
            return optionalPortfolioJob.get();
        }

        throw new NotFoundException("Portfolio's Job does not exists");
    }

    public PortfolioJob getOneCompleteById(Long id) {
        Optional<PortfolioJob> optionalPortfolioJob = Optional.ofNullable(this.portfolioJobRepository.findPortfolioJobCompleteById(id));

        if (optionalPortfolioJob.isPresent()) {
            return optionalPortfolioJob.get();
        }

        throw new NotFoundException("Portfolio's Job does not exists");
    }

    public void deletePortfolioJob(Long id) {
        if (this.portfolioJobRepository.existsById(id)) {
            this.portfolioJobRepository.deleteById(id);
        }
    }

}

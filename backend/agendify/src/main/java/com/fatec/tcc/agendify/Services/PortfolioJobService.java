package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.Image;
import com.fatec.tcc.agendify.Entities.JobCategory;
import com.fatec.tcc.agendify.Entities.Portfolio;
import com.fatec.tcc.agendify.Entities.RequestTemplate.PortfolioJobBody;
import com.fatec.tcc.agendify.Entities.RequestTemplate.PortfolioJobResponse;
import com.fatec.tcc.agendify.Repositories.ImageRepository;
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
    private ImageRepository imageRepository;

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

    public PortfolioJobResponse createPortfolioJob(PortfolioJobBody portfolioJobBody) {

        try {
            Optional<Portfolio> optionalPortfolio = this.portfolioRepository.findById(portfolioJobBody.portfolioId());
            Optional<JobCategory> optionalJob = this.jobCategoryRepository.findById(portfolioJobBody.jobId());

            if (optionalPortfolio.isEmpty()) throw new IllegalArgumentException("Invalid portfolio for portfolio job!");
            if (optionalJob.isEmpty()) throw new IllegalArgumentException("Invalid job for portfolio job!");

            Portfolio portfolio = optionalPortfolio.get();
            JobCategory jobCategory = optionalJob.get();

            if (!Objects.equals(portfolio.getCategory().getId(), jobCategory.getSubCategory().getCategory().getId()))
                throw new IllegalArgumentException("An error occurred! The job selected does not belong to the portfolio category!");

            String name = portfolioJobBody.name() == null || portfolioJobBody.name().isEmpty()
                    ? jobCategory.getName()
                    : portfolioJobBody.name();

            if (portfolioJobBody.price() <= 0)
                throw new IllegalArgumentException("An error occurred! Invalid job price!");

            Image imageCover;

            if (Objects.isNull(portfolioJobBody.coverImage()) || portfolioJobBody.coverImage().isBlank()) {
                imageCover = null;
            } else {
                imageCover = this.imageService.saveImage(portfolioJobBody.coverImage());
            }

            PortfolioJob newPortfolioJob = this.portfolioJobRepository.save(new PortfolioJob(
                    name,
                    portfolioJobBody.price(),
                    portfolioJobBody.description(),
                    imageCover == null ? null : imageCover.getId(),
                    portfolio,
                    jobCategory,
                    portfolioJobBody.duration().toString(),
                    portfolioJobBody.restricted())
            );

            System.out.println(newPortfolioJob.getId());

            this.portfolioJobRepository.save(newPortfolioJob);
            if (Objects.nonNull(portfolioJobBody.serviceImages())) {
                portfolioJobBody
                        .serviceImages()
                        .forEach(img -> this.imageService.saveImage(img, newPortfolioJob));
            }

            List<Image> imageList = this.imageRepository.findAllByPortfolioJob_Id(newPortfolioJob.getId());
            List<String> images = new ArrayList<>();

            if (!imageList.isEmpty()) {
                imageList.forEach(img -> images.add(img.getBase64()));
            }

            String cover = null;
            if (newPortfolioJob.getImageCoverId() != null) {
                Optional<Image> optionalImage = this.imageRepository.findById(newPortfolioJob.getImageCoverId());
                if (optionalImage.isPresent()) {
                    cover = optionalImage.get().getBase64();
                }
            }
            return new PortfolioJobResponse(
                    newPortfolioJob.getId(),
                    newPortfolioJob.getName(),
                    newPortfolioJob.getPrice(),
                    newPortfolioJob.getDescription(),
                    newPortfolioJob.getDuration() == null ? null : newPortfolioJob.getDuration().toString(),
                    newPortfolioJob.getPortfolio().getId(),
                    newPortfolioJob.getPortfolio().getCategory().getName(),
                    newPortfolioJob.getJobCategory().getName(),
                    newPortfolioJob.getPortfolio().getCompanyBranch().getUser().getFirstName()
                            +' '+newPortfolioJob.getPortfolio().getCompanyBranch().getUser().getLastName(),
                    newPortfolioJob.getRestricted(),
                    cover,
                    images
            );

        } catch (Exception e) {
            logger.error("Error on create portfolio job: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
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

    public List<PortfolioJobResponse> getAll(){
        Iterable<PortfolioJob> iterable = this.portfolioJobRepository.findAll();
        List<PortfolioJobResponse> jobResponses = new ArrayList<>();

        iterable.forEach(portfolioJob -> {
            List<Image> imageList = this.imageRepository.findAllByPortfolioJob_Id(portfolioJob.getId());
            List<String> images = new ArrayList<>();
            String cover = null;

            if (portfolioJob.getImageCoverId() != null) {
                Optional<Image> optionalImage = this.imageRepository.findById(portfolioJob.getImageCoverId());
                if (optionalImage.isPresent()) {
                    cover = optionalImage.get().getBase64();
                }
            }

            if (!imageList.isEmpty()) {
                imageList.forEach(img -> images.add(img.getBase64()));
            }

            jobResponses.add(new PortfolioJobResponse(
                    portfolioJob.getId(),
                    portfolioJob.getName(),
                    portfolioJob.getPrice(),
                    portfolioJob.getDescription(),
                    portfolioJob.getDuration(),
                    portfolioJob.getPortfolio().getId(),
                    portfolioJob.getPortfolio().getCategory().getName(),
                    portfolioJob.getJobCategory().getName(),
                    portfolioJob.getPortfolio().getCompanyBranch().getUser().getFirstName() + ' '+
                            portfolioJob.getPortfolio().getCompanyBranch().getUser().getLastName(),
                    false,
                    cover,
                    images
            ));
        });

        return jobResponses;
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

    public PortfolioJobResponse getById(Long id) {
        Optional<PortfolioJob> optionalPortfolioJob = this.portfolioJobRepository.findById(id);

        if (optionalPortfolioJob.isPresent()) {
            PortfolioJob job = optionalPortfolioJob.get();
            List<Image> imageList = this.imageRepository.findAllByPortfolioJob_Id(id);
            List<String> images = new ArrayList<>();
            String cover = null;

            if (job.getImageCoverId() != null) {
                Optional<Image> optionalImage = this.imageRepository.findById(job.getImageCoverId());
                if (optionalImage.isPresent()) {
                    cover = optionalImage.get().getBase64();
                }
            }

            if (!imageList.isEmpty()) {
                imageList.forEach(img -> images.add(img.getBase64()));
            }

            return new PortfolioJobResponse(
                    job.getId(),
                    job.getName(),
                    job.getPrice(),
                    job.getDescription(),
                    job.getDuration() == null ? null : job.getDuration(),
                    job.getPortfolio().getId(),
                    job.getPortfolio().getCategory().getName(),
                    job.getJobCategory().getName(),
                    job.getPortfolio().getCompanyBranch().getUser().getFirstName()
                            +' '+job.getPortfolio().getCompanyBranch().getUser().getLastName(),
                    job.getRestricted(),
                    cover,
                    images
            );
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

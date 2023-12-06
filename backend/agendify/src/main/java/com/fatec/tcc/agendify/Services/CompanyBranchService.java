package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.*;
import com.fatec.tcc.agendify.Entities.Address.Address;
import com.fatec.tcc.agendify.Entities.RequestTemplate.*;
import com.fatec.tcc.agendify.Repositories.*;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyBranchService {
    Logger logger = LoggerFactory.getLogger(CepApiService.class);

    @Autowired
    private CompanyBranchRepository companyBranchRepository;

    @Autowired
    private PortfolioJobRepository portfolioJobRepository;

    @Autowired
    private BusinessHourRepository businessHourRepository;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private BusinessHourService businessHourService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserRepository userRepository;

    public List<CompanyBranch> getAll(){
        return (List<CompanyBranch>) this.companyBranchRepository.findAll();
    }

    public EnterpriseProfileResponse getProfile(Long id) {
        Optional<CompanyBranch> optionalCompanyBranch = this.companyBranchRepository.findById(id);

        if (optionalCompanyBranch.isPresent()) {
            Image profile = null;
            Image cover = null;
            List<PortfolioJobResponse> portfolioJobResponse = new ArrayList<>();
            CompanyBranch company = optionalCompanyBranch.get();
            List<BussinesHourDataResponse> bussinesHourDataResponse = new ArrayList<>();
            List<BussinessHour> bussinessHourList = this.businessHourRepository.findAllByPortfolio_CompanyBranch_Id(id);
            List<PortfolioJob> portfolioJobList =
                    this.portfolioJobRepository.findAllByPortfolio_CompanyBranch_User_Id(company.getUser().getId());

            if (!bussinessHourList.isEmpty()) {
                bussinessHourList.forEach(bussinessHour -> {
                    bussinesHourDataResponse.add(
                            new BussinesHourDataResponse(
                                    bussinessHour.getDayOfWeek().name(),
                                    bussinessHour.getStartTime().toString(),
                                    bussinessHour.getEndTime().toString()
                            )
                    );
                });
            }

            if (!portfolioJobList.isEmpty()) {
                List<String> images = new ArrayList<>();

                for (int i = 0; i < portfolioJobList.size(); i++) {
                    portfolioJobList.get(i).getImages().forEach(img -> { images.add(img.getBase64()); });
                    String coverImage = null;

                    if (Objects.nonNull(portfolioJobList.get(i).getImageCoverId())) {
                        Optional<Image> optionalImage = this.imageRepository.findById(portfolioJobList.get(i).getImageCoverId());
                        if (optionalCompanyBranch.isPresent()) coverImage = optionalImage.get().getBase64();
                    }


                    portfolioJobResponse.add(new PortfolioJobResponse(
                            portfolioJobList.get(i).getId(),
                            portfolioJobList.get(i).getName(),
                            portfolioJobList.get(i).getPrice(),
                            portfolioJobList.get(i).getDescription(),
                            portfolioJobList.get(i).getDuration() == null ? null : portfolioJobList.get(i).getDuration().toString(),
                            portfolioJobList.get(i).getPortfolio().getId(),
                            portfolioJobList.get(i).getPortfolio().getCategory().getName(),
                            portfolioJobList.get(i).getJobCategory().getName(),
                            portfolioJobList.get(i).getPortfolio().getCompanyBranch().getUser().getFirstName()
                                    +portfolioJobList.get(i).getPortfolio().getCompanyBranch().getUser().getLastName(),
                            portfolioJobList.get(i).getRestricted(),
                            coverImage,
                            images
                        )
                    );
                }
            }

            if (Objects.nonNull(company.getUser().getImageProfileId())) {
                Optional<Image> optionalImage = this.imageRepository.findById(company.getUser().getImageProfileId());

                profile = optionalImage.orElse(null);
            }

            if (Objects.nonNull(company.getUser().getImageCoverId())) {
                Optional<Image> optionalImage = this.imageRepository.findById(company.getUser().getImageCoverId());
                cover = optionalImage.orElse(null);
            }

            return new EnterpriseProfileResponse(
                    company.getName(),
                    company.getDescription(),
                    portfolioJobResponse,
                    profile == null ? "" : profile.getBase64(),
                    cover == null ? "" : cover.getBase64(),
                    company.getAddress() == null
                            ? null
                            : new AddressResponse(
                            company.getAddress().getCep(),
                            company.getAddress().getNeighborhood().getName(),
                            company.getAddress().getNeighborhood().getCity().getName(),
                            company.getAddress().getNeighborhood().getCity().getState().getNameAbbreviation(),
                            company.getAddress().getPublicPlace(),
                            company.getAddress().getNumber(),
                            company.getAddress().getComplement()
                    ),
                    bussinesHourDataResponse
            );
        }

        throw new NotFoundException("Company branch does not exists");
    }
    public CompanyBranch getById(Long id) {
        Optional<CompanyBranch> optionalCompanyBranch = this.companyBranchRepository.findById(id);

        if (optionalCompanyBranch.isPresent()) {
            return optionalCompanyBranch.get();
        }

        throw new NotFoundException("Company branch does not exists");
    }

    public List<CompanyBranch> getByUserId(Long id) {
        return this.companyBranchRepository.findAllByUser_Id(id);
    }

    public CompanyBranch create(CompanyBranchBody companyBranch) {
        try {
            if (Strings.trimToNull(companyBranch.getFantasyName()) == null
                    || companyBranch.getFantasyName().isEmpty()
                    || companyBranch.getFantasyName().isBlank()) {
                throw new IllegalUserArgumentException("Company branch's name is required");
            }

            if (Objects.isNull(companyBranch.getUserId())) {
                throw new IllegalUserArgumentException("Company branch user id is required");
            }
            String branchName = companyBranch.getFantasyName();
            CepApi address = companyBranch.getAddress();
            Long userId = companyBranch.getUserId();

            Optional<User> optionalUser = this.userRepository.findById(userId);
            List<CompanyBranch> companyBranchList = this.companyBranchRepository.findByName(branchName);

            if (optionalUser.isEmpty()) throw new IllegalArgumentException("Invalid user id! Owner not found");

            User user = optionalUser.get();
            boolean existsUserCompanyWithName = false;

            for (int i = 0; i < companyBranchList.size(); i++) {
                if (Objects.equals(companyBranchList.get(i).getUser().getId(), userId)) {
                    existsUserCompanyWithName = true;
                    break;
                }
            }

            if (companyBranchList.isEmpty() || !existsUserCompanyWithName) {
                return this.companyBranchRepository.save(
                        new CompanyBranch(branchName,
                                this.addressService.createAndGetAddress(address),
                                user,
                                companyBranch.getDescription(),
                                companyBranch.getIs24Hours())
                );
            }

            throw new IllegalArgumentException("This company name is already registered in this user account");
        } catch (Exception e) {
            logger.error("Error on create new company: " + e.getMessage());
            throw  e;
        }

    }

    public void createCompanyBranchWithCategoriesAndSubcategories(CompanyBranchBody companyBranch) {

        CompanyBranch companyCreated =
                this.create(
                        companyBranch
                );

        Portfolio newPortfolio = this.portfolioService.createPortfolio(
                companyCreated.getId(),
                companyBranch.getCategory(),
                companyBranch.getSubCategories());

    }
// TODO renomear public place para street name
    public void updateCompanyBranch(Long companyId, CompanyBranchBody companyBranchBody) {
        Optional<CompanyBranch> optionalCompanyBranch = this.companyBranchRepository.findById(companyId);
        Optional<User> optionalUser = this.userRepository.findById(companyBranchBody.getUserId());

        if (optionalUser.isEmpty()) throw new IllegalArgumentException("Invalid user id! Owner not found");
        User user = optionalUser.get();

        if (optionalCompanyBranch.isEmpty())
            throw new NotFoundException("Company does not exists!");
        CompanyBranch companyBranchToUpdate = optionalCompanyBranch.get();

        if (Objects.nonNull(companyBranchBody.getAddress())) {
            Address address1 = this.addressService.createAndGetAddress(companyBranchBody.getAddress());
            companyBranchToUpdate.setAddress(address1);
            this.companyBranchRepository.save(companyBranchToUpdate);
        }

//        if (Objects.isNull(companyBranchBody.getFantasyName()) || this.companyBranchRepository
//                .existsByNameAndUser_Id(companyBranchToUpdate.getName(), user.getId())
//        )
        List<CompanyBranch> companyBranchList = this.companyBranchRepository.findByName(companyBranchBody.getFantasyName());
        boolean existsUserCompanyWithName = false;

        for (int i = 0; i < companyBranchList.size(); i++) {
            if (Objects.equals(companyBranchList.get(i).getUser().getId(), companyBranchBody.getUserId())) {
                existsUserCompanyWithName = true;
                break;
            }
        }

        if (companyBranchList.isEmpty() || !existsUserCompanyWithName) {
            companyBranchToUpdate.setName(companyBranchBody.getFantasyName());
            this.companyBranchRepository.save(companyBranchToUpdate);
            return;
        }
        throw new IllegalArgumentException("This company name is already registered in this user account");

//        companyBranchToUpdate.setName(companyBranchBody.getFantasyName());


    }

    public void deleteSubCategory(Long id) {
        if (this.companyBranchRepository.existsById(id)) {
            this.companyBranchRepository.deleteById(id);
        }
    }

}

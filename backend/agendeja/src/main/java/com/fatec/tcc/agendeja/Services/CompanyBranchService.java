package com.fatec.tcc.agendeja.Services;

import com.fatec.tcc.agendeja.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendeja.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendeja.Entities.*;
import com.fatec.tcc.agendeja.Entities.Address.Address;
import com.fatec.tcc.agendeja.Entities.RequestTemplate.CepApi;
import com.fatec.tcc.agendeja.Entities.RequestTemplate.CompanyBranchBody;
import com.fatec.tcc.agendeja.Repositories.CompanyBranchRepository;
import com.fatec.tcc.agendeja.Repositories.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CompanyBranchService {
    Logger logger = LoggerFactory.getLogger(CepApiService.class);

    @Autowired
    private CompanyBranchRepository companyBranchRepository;

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private UserRepository userRepository;

    public List<CompanyBranch> getAll(){
        return (List<CompanyBranch>) this.companyBranchRepository.findAll();
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

    public CompanyBranch createCompanyBranch(CompanyBranchBody companyBranch) {
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
                                this.addressService.createAddress(address),
                                user)
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
                this.createCompanyBranch(
                        companyBranch
                );

        this.portfolioService.createPortfolio(
                companyCreated.getId(),
                companyBranch.getCategory(),
                companyBranch.getSubCategories());

    }

    public void updateCompanyBranch(Long id, Address address) {
        Optional<CompanyBranch> optionalCompanyBranch = this.companyBranchRepository.findById(id);

        if (optionalCompanyBranch.isPresent()) {
            CompanyBranch companyBranchToUpdate = optionalCompanyBranch.get();

            if (Objects.nonNull(address)) {
                companyBranchToUpdate.setAddress(address);
                this.companyBranchRepository.save(companyBranchToUpdate);
                return;
            }

        }
        throw new NotFoundException("Company does not exists!");

    }

    public void deleteSubCategory(Long id) {
        if (this.companyBranchRepository.existsById(id)) {
            this.companyBranchRepository.deleteById(id);
        }
    }

}

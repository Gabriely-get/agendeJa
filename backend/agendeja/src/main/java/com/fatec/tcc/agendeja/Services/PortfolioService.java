package com.fatec.tcc.agendeja.Services;

import com.fatec.tcc.agendeja.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendeja.Entities.*;
import com.fatec.tcc.agendeja.Repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {
    Logger logger = LoggerFactory.getLogger(PortfolioService.class);

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private CompanyBranchRepository companyRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Portfolio> getAll(){
        return (List<Portfolio>) this.portfolioRepository.findAll();
    }

    public Portfolio getById(Long id) {
        Optional<Portfolio> optionalPortfolio = this.portfolioRepository.findById(id);

        if (optionalPortfolio.isPresent()) {
            return optionalPortfolio.get();
        }

        throw new NotFoundException("Portfolio does not exists");
    }
    public List<Portfolio> getByUserId(Long id) {
        return this.portfolioRepository.findAllByCompanyBranch_User_Id(id);
    }

    public List<SubCategory> getAllSubcategoriesPortfolioDoesNotHave(Long portfolioId) {
        //TODO finish validations here
        Optional<Portfolio> optionalPortfolio = this.portfolioRepository.findById(portfolioId);
        Portfolio portfolio = optionalPortfolio.get();

        List<SubCategory> userCategories = portfolio.getSubCategories().stream().toList();

        List<SubCategory> categories = this.subCategoryRepository.getAllByCategory_Id(portfolio.getCategory().getId());
        List<SubCategory> categoriesPortfolioDoesNotHave = new ArrayList<>();

        for (int i = 0; i < categories.size(); i++) {
            if (!userCategories.contains(categories.get(i)))
                categoriesPortfolioDoesNotHave.add(categories.get(i));
        }

        return categoriesPortfolioDoesNotHave;
    }

    public void addSubcategoryToPortfolio(Long portfolioId, Long subId) {
        //TODO finish validations here too
        Optional<Portfolio> optionalPortfolio = this.portfolioRepository.findById(portfolioId);
        Portfolio portfolio = optionalPortfolio.get();

        Optional<SubCategory> optionalSubCategory = this.subCategoryRepository.findById(subId);
        SubCategory subCategory1 = optionalSubCategory.get();

        portfolio.getSubCategories().add(subCategory1);
        this.portfolioRepository.save(portfolio);
    }

    public Portfolio createPortfolio(Long companyBranchId, Long categoryId, List<Long> subCategories) {
        Set<SubCategory> validSubCategories = new HashSet<>();
        Optional<CompanyBranch> optionalCompanyBranch = this.companyRepository.findById(companyBranchId);
        Optional<Category> optionalCategory = this.categoryRepository.findById(categoryId);

        if (this.portfolioRepository.existsByCategory_IdAndCompanyBranch_Id(categoryId, companyBranchId))
            throw new RuntimeException("A portfolio with that category already exists!");

        if (optionalCompanyBranch.isEmpty()) throw new IllegalArgumentException("Invalid company! Company not found");
        CompanyBranch companyBranch = optionalCompanyBranch.get();

        if (optionalCategory.isEmpty()) throw new IllegalArgumentException("Invalid Category! Category not found");
        Category category = optionalCategory.get();
        boolean anyCategoryExists = false;

        List<Long> distinctCategories = subCategories.stream().distinct().toList();
        for (int i = 0; i < distinctCategories.size(); i++) {
            Optional<SubCategory> validSubCategory = this.subCategoryRepository.findById(distinctCategories.get(i));

            if (validSubCategory.isPresent()) {
                anyCategoryExists = true;
                SubCategory vc = validSubCategory.get();

                if (vc.getCategory().getId().equals(categoryId)) {
                    validSubCategories.add(validSubCategory.get());
                }
            }
        }

        if (!anyCategoryExists) throw new IllegalArgumentException("Given sub categories does not exist!");

        if (!validSubCategories.isEmpty()) {
            return this.portfolioRepository.save(new Portfolio(companyBranch, category, validSubCategories));
        }

        throw new IllegalArgumentException("This sub categories are not part of the given category!");
        //TODO confirmation for delete category because many stuff will depends on of it ?

    }

    public void updatePortfolio(Long id, List<Long> newSubCategories) {
        Optional<Portfolio> optionalPortfolio = this.portfolioRepository.findById(id);

        if (optionalPortfolio.isPresent()) {
            Portfolio portfolioToUpdate = optionalPortfolio.get();
            Set<SubCategory> validSubCategories = new HashSet<>();
            boolean anyCategoryExists = false;

            List<Long> distinctSubCategories = newSubCategories.stream().distinct().toList();
            for (int i = 0; i < distinctSubCategories.size(); i++) {
                Optional<SubCategory> validSubCategory =
                        this.subCategoryRepository.findById(distinctSubCategories.get(i));

                if (validSubCategory.isPresent()) {
                    anyCategoryExists = true;
                    SubCategory vsc = validSubCategory.get();

                    if (vsc.getCategory().getId().equals(portfolioToUpdate.getCategory().getId())) {
                        validSubCategories.add(validSubCategory.get());
                    }
                }
            }

            if (!anyCategoryExists) throw new IllegalArgumentException("Given sub categories does not exist!");

            if (!validSubCategories.isEmpty()) {
                portfolioToUpdate.setSubCategories(validSubCategories);
                this.portfolioRepository.save(portfolioToUpdate);
                return;
            }

            throw new IllegalArgumentException("This sub categories are not part of the portfolio category!");

        }

        throw new NotFoundException("Portfolio does not exists");
    }

    public void deletePortfolio(Long id) {
        if (this.portfolioRepository.existsById(id)) {
            this.portfolioRepository.deleteById(id);
        }
    }

    public void deleteSubcategoryFromPortfolio(Long userId, Long portfolioId, Long subcategoryId) {
        try {
            Optional<Portfolio> optionalPortfolio = this.portfolioRepository.findById(portfolioId);
            Optional<User> optionalUser = this.userRepository.findById(userId);
            Optional<SubCategory> optionalSubCategory = this.subCategoryRepository.findById(subcategoryId);
            boolean existsPortfoliosUser = this.portfolioRepository.existsPortfolioByIdAndCompanyBranch_User_Id(portfolioId, userId);

            if (optionalPortfolio.isEmpty()) throw new RuntimeException("Invalid portfolio for delete subcategory");
            if (optionalUser.isEmpty()) throw new RuntimeException("Invalid user for delete subcategory");
            if (optionalSubCategory.isEmpty()) throw new RuntimeException("Invalid sub category for delete subcategory");
            if (!existsPortfoliosUser) throw new RuntimeException("The given portfolio does not belong to te user! Can not delete subcategory");

            Portfolio portfolio = optionalPortfolio.get();
            SubCategory subCategory = optionalSubCategory.get();

            portfolio.getSubCategories().remove(subCategory);
            this.portfolioRepository.save(portfolio);

        } catch (Exception e) {
            logger.error("Error on remove subcategory from portfolio: " + e.getMessage());
            throw  e;
        }

    }

}

package com.fatec.tcc.agendify.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendify.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendify.Entities.Portfolio;
import com.fatec.tcc.agendify.Entities.RequestTemplate.PortfolioBody;
import com.fatec.tcc.agendify.Entities.SubCategory;
import com.fatec.tcc.agendify.Services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/agenda/portfolio")
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> getPortfolio(@PathVariable("id") Long id) {
        try {
            Portfolio portfolio = this.portfolioService.getById(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(portfolio).build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ObjectNode> getPortfoliosByUser(@PathVariable("id") Long id) {
        try {
            List<Portfolio> portfolios = this.portfolioService.getByUserId(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(portfolios).build(), HttpStatus.OK);
        } catch (JsonProcessingException | RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ObjectNode> getPortfolios() {
        try {
            List<Portfolio> portfolios = this.portfolioService.getAll();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(portfolios).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/subcategories/{id}")
    public ResponseEntity<ObjectNode> getSubcategoriesPortfolioDoesNotHave(@PathVariable("id") Long portfolioId) {
        try {
            List<SubCategory> subCategories = this.portfolioService.getAllSubcategoriesPortfolioDoesNotHave(portfolioId);

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(subCategories).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ObjectNode> createPortfolio(@RequestBody PortfolioBody portfolioBody) {
        try {

            Portfolio portfolio = this.portfolioService.createPortfolio(
                    portfolioBody.getCompanyBranchId(),
                    portfolioBody.getCategoryId(),
                    portfolioBody.getSubcategories()
            );

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(portfolio).build(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectNode> updatePortfolio(@PathVariable("id") Long id, @RequestBody PortfolioBody portfolioBody) {
        try {
            this.portfolioService.updatePortfolio(id, portfolioBody.getSubcategories());

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<ObjectNode> addSubcategoryToPortfolio(@PathVariable("id") Long id, @RequestBody PortfolioBody portfolioBody) {
        try {
            this.portfolioService.addSubcategoryToPortfolio(id, portfolioBody.getSubCategoryId());

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectNode> deletePortfolio(@PathVariable("id") Long id) {
        try {
            this.portfolioService.deletePortfolio(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/subcategory/{id}")
    public ResponseEntity<ObjectNode> deleteSubcategoryFromPortfolio(@PathVariable("id") Long portfolioId, @RequestBody() PortfolioBody portfolioBody) {
        try {
            this.portfolioService.deleteSubcategoryFromPortfolio(portfolioBody.getUserId(), portfolioId, portfolioBody.getSubCategoryId());

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }
    
}

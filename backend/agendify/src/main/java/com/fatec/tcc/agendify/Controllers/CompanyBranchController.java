package com.fatec.tcc.agendify.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendify.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.CompanyBranch;
import com.fatec.tcc.agendify.Entities.RequestTemplate.CompanyBranchBody;
import com.fatec.tcc.agendify.Services.CompanyBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/agenda/company")
public class CompanyBranchController {
    @Autowired
    private CompanyBranchService companyBranchService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> getCompanyBranch(@PathVariable("id") Long id) {
        try {
            CompanyBranch branch = this.companyBranchService.getById(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(branch).build(), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ObjectNode> getCompanyBranchByUser(@PathVariable("id") Long id) {
        try {
            List<CompanyBranch> branches = this.companyBranchService.getByUserId(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(branches).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ObjectNode> getCompanyBranches() {
        try {
            List<CompanyBranch> companyBranches = this.companyBranchService.getAll();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(companyBranches).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ObjectNode> createCompanyBranch(@RequestBody CompanyBranchBody company) {
        try {
            this.companyBranchService.create(company);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ObjectNode> createCompanyBranchWithCatAndSubCat(@RequestBody CompanyBranchBody company) {
        try {
            this.companyBranchService.createCompanyBranchWithCategoriesAndSubcategories(company);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectNode> updateCompanyBranch(@PathVariable("id") Long companyId, @RequestBody CompanyBranchBody company) {
        try {
            this.companyBranchService.updateCompanyBranch(companyId, company);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectNode> deleteCompanyBranch(@PathVariable("id") Long id) {
        try {
            this.companyBranchService.deleteSubCategory(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

}

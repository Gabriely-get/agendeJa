package com.fatec.tcc.agendeja.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendeja.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendeja.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendeja.Entities.PortfolioJob;
import com.fatec.tcc.agendeja.Entities.RequestTemplate.NameAndIdBody;
import com.fatec.tcc.agendeja.Entities.JobCategory;
import com.fatec.tcc.agendeja.Services.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/agenda/job")
public class JobCategoryController {
    @Autowired
    private JobCategoryService jobCategoryService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> getJob(@PathVariable("id") Long id) {
        try {
            JobCategory jobCategory = this.jobCategoryService.getById(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(jobCategory).build(), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/subcategory/{id}")
    public ResponseEntity<ObjectNode> getJobCategoriesBySubcategoryId(@PathVariable("id") Long id) {
        try {
            List<JobCategory> jobCategory = this.jobCategoryService.getAllJobCategoriesBySubcategory(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(jobCategory).build(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ObjectNode> getJobs() {
        try {
            List<JobCategory> jobCategories = this.jobCategoryService.getAll();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(jobCategories).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ObjectNode> createJob(@RequestBody NameAndIdBody nameAndIdBody) {
        try {

            this.jobCategoryService.createJob(nameAndIdBody);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectNode> updateJob(@PathVariable("id") Long id, @RequestBody NameAndIdBody nameAndIdBody) {
        try {
            this.jobCategoryService.updateJob(id, nameAndIdBody.getName(), nameAndIdBody.getId());

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectNode> deleteJob(@PathVariable("id") Long id) {
        try {
            this.jobCategoryService.deleteJob(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }
}

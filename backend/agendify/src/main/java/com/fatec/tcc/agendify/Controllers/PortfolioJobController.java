package com.fatec.tcc.agendify.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendify.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.PortfolioJob;
import com.fatec.tcc.agendify.Entities.RequestTemplate.ErrorResponseAPI;
import com.fatec.tcc.agendify.Entities.RequestTemplate.PortfolioJobBody;
import com.fatec.tcc.agendify.Entities.RequestTemplate.PortfolioJobResponse;
import com.fatec.tcc.agendify.Services.PortfolioJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/agenda/userjob")
public class PortfolioJobController {

    @Autowired
    private PortfolioJobService userJobService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPortfolioById(@PathVariable("id") Long id) {
        try {
            PortfolioJobResponse portfolioJob = this.userJobService.getById(id);

            return ResponseEntity.ok(portfolioJob);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseAPI(e.getMessage()));
        }
    }

    @GetMapping("/")
    public ResponseEntity<ObjectNode> getPortfolioJobs() {
        try {
            List<PortfolioJob> portfolioJobs = this.userJobService.getAll();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(portfolioJobs).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by/{id}")
    public ResponseEntity<ObjectNode> getAllPortfolioJobsByUserId(@PathVariable("id") Long id) {
        try {
            List<PortfolioJob> portfolioJobs = this.userJobService.getAllByUserId(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(portfolioJobs).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by/{userId}/{subCatId}")
    public ResponseEntity<ObjectNode> getAllPortfolioJobsByUserIdAndSubCategoryId(
            @PathVariable("userId") Long userId, @PathVariable("subCatId") Long subCategoryId) {
        try {
            List<PortfolioJob> portfolioJobs = this.userJobService.getAllByUserIdAndSubCategoryId(userId, subCategoryId);

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(portfolioJobs).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createPortfolioJob(@RequestBody PortfolioJobBody portfolioBody) {
        try {

            PortfolioJobResponse portfolioJob = this.userJobService.createPortfolioJob(portfolioBody);

            return ResponseEntity.ok(portfolioJob);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ErrorResponseAPI(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectNode> updatePortfolioJob(@PathVariable("id") Long id, @RequestBody PortfolioJob portfolioJob) {
        try {
            this.userJobService.updatePortfolioJob(id, portfolioJob);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectNode> deletePortfolioJob(@PathVariable("id") Long id) {
        try {
            this.userJobService.deletePortfolioJob(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

}

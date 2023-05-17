package com.fatec.tcc.agendeja.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendeja.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendeja.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendeja.Entities.SubCategory;
import com.fatec.tcc.agendeja.Entities.RequestTemplate.NameAndIdBody;
import com.fatec.tcc.agendeja.Services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/agenda/subcategory")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> getSubCategory(@PathVariable("id") Long id) {
        try {
            SubCategory subCategory = this.subCategoryService.getById(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(subCategory).build(), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/by/{id}")
    public ResponseEntity<ObjectNode> getSubCategoryByCategoryId(@PathVariable("id") Long id) {
        try {
            List<SubCategory> subCategory = this.subCategoryService.getByCategoryId(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(subCategory).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ObjectNode> getSubCategories() {
        try {
            List<SubCategory> subCategories = this.subCategoryService.getAll();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(subCategories).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ObjectNode> createSubCategory(@RequestBody NameAndIdBody nameAndIdBody) {
        try {

            this.subCategoryService.createSubCategory(nameAndIdBody);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectNode> updateSubCategory(@PathVariable("id") Long id, @RequestBody NameAndIdBody nameAndIdBody) {
        try {
            this.subCategoryService.updateSubCategory(id, nameAndIdBody.getName(), nameAndIdBody.getId());

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectNode> deleteSubCategory(@PathVariable("id") Long id) {
        try {
            this.subCategoryService.deleteSubCategory(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

}

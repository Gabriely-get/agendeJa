package com.fatec.tcc.agendeja.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fatec.tcc.agendeja.Builders.JsonResponseBuilder;
import com.fatec.tcc.agendeja.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendeja.Entities.Category;
import com.fatec.tcc.agendeja.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/agenda/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private JsonResponseBuilder jsonResponseBuilder;

    @GetMapping("/{id}")
    public ResponseEntity<ObjectNode> getCategory(@PathVariable("id") Long id) {
        try {
            Category category = this.categoryService.getById(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withBody(category).build(), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/")
    public ResponseEntity<ObjectNode> getCategories() {
        try {
            List<Category> categories = this.categoryService.getAll();

            return new ResponseEntity<>(this.jsonResponseBuilder.withList(categories).build(), HttpStatus.OK);
        } catch (RuntimeException | JsonProcessingException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ObjectNode> createCategory(@RequestBody Category category) {
        try {
            this.categoryService.createCategory(category.getName());

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.CREATED);
        } catch (RuntimeException | SQLIntegrityConstraintViolationException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObjectNode> updateCategory(@PathVariable("id") Long id, @RequestBody Category category) {
        try {
            this.categoryService.updateCategory(id, category.getName());

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ObjectNode> deleteCategory(@PathVariable("id") Long id) {
        try {
            this.categoryService.deleteCategory(id);

            return new ResponseEntity<>(this.jsonResponseBuilder.withoutMessage().build(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(this.jsonResponseBuilder.withError(e.getMessage()).build(), HttpStatus.BAD_REQUEST);
        }
    }
}

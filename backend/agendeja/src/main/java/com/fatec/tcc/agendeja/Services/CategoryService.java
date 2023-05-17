package com.fatec.tcc.agendeja.Services;

import com.fatec.tcc.agendeja.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendeja.Entities.Category;
import com.fatec.tcc.agendeja.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void createCategory(String name) throws SQLIntegrityConstraintViolationException {
        Optional<Category> optionalCategory = this.categoryRepository.findByName(name);

        if (optionalCategory.isEmpty()) {
            this.categoryRepository.save(new Category(name));
            return;
        }

        throw new IllegalArgumentException("Category name is already registered");
        //TODO require confirmation for delete category because many stuff will depends on of it

    }

    public List<Category> getAll(){
        Iterable<Category> iterableUsers = this.categoryRepository.findAll();
        List<Category> categoryList = new ArrayList<>();
        iterableUsers.forEach(categoryList::add);

        return categoryList;
    }

    public Category getById(Long id) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }

        throw new NotFoundException("Category does not exists");
    }

    public Category getByName(String name) {
        Optional<Category> optionalCategory = this.categoryRepository.findByName(name);

        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }

        throw new NotFoundException("Category does not exists");
    }

    public void updateCategory(Long id, String newName) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            Category categoryToUpdate = optionalCategory.get();

            Optional<Category> optionalCategory1 = this.categoryRepository.findByName(newName);

            if (optionalCategory1.isPresent()) {
                Category category = optionalCategory1.get();

                if (!Objects.equals(id, category.getId())) throw new IllegalArgumentException("Category already registered");
                else {
                    categoryToUpdate.setName(newName);
                    this.categoryRepository.save(categoryToUpdate);
                    return;
                }
            }

            categoryToUpdate.setName(newName);
            this.categoryRepository.save(categoryToUpdate);
            return;

        }

        throw new NotFoundException("Category does not exists");
    }

    public void deleteCategory(Long id) {
        if (this.categoryRepository.existsById(id)) {
            this.categoryRepository.deleteById(id);
        }
    }

}

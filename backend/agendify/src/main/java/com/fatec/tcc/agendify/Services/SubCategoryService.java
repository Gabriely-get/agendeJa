package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.Category;
import com.fatec.tcc.agendify.Entities.SubCategory;
import com.fatec.tcc.agendify.Entities.RequestTemplate.NameAndIdBody;
import com.fatec.tcc.agendify.Repositories.CategoryRepository;
import com.fatec.tcc.agendify.Repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    //TODO first mandatory subcategory save as one in the array of the filial
    public void createSubCategory(NameAndIdBody nameAndIdBody) {
        Optional<Category> optionalCategory = this.categoryRepository.findById(nameAndIdBody.getId());
        Optional<SubCategory> optionalSubCategory = this.subCategoryRepository.findByName(nameAndIdBody.getName());

        if (optionalCategory.isEmpty()) throw new IllegalArgumentException("Invalid category!");
        Category category = optionalCategory.get();

        if (optionalSubCategory.isEmpty()) {
            this.subCategoryRepository.save(new SubCategory(nameAndIdBody.getName(), category));
            return;
        }

        throw new IllegalArgumentException("Sub category name is already registered");
    }

    public List<SubCategory> getAll(){
        Iterable<SubCategory> iterableUsers = this.subCategoryRepository.findAll();
        List<SubCategory> subCategoryList = new ArrayList<>();
        iterableUsers.forEach(subCategoryList::add);

        return subCategoryList;
    }

//    public List<SubCategory> getAll(Long userId, Long portfolioId){
//        Optional<User> optionalUser = this.
//
//        Iterable<SubCategory> iterableUsers = this.subCategoryRepository.findAll();
//        List<SubCategory> subCategoryList = new ArrayList<>();
//        iterableUsers.forEach(subCategoryList::add);
//
//        return subCategoryList;
//    }

    public SubCategory getById(Long id) {
        Optional<SubCategory> optionalSubCategory = this.subCategoryRepository.findById(id);

        if (optionalSubCategory.isPresent()) {
            return optionalSubCategory.get();
        }

        throw new NotFoundException("Sub category does not exists");
    }

    public List<SubCategory> getByCategoryId(Long id){
        Iterable<SubCategory> iterableUsers = this.subCategoryRepository.getAllByCategory_Id(id);
        List<SubCategory> subCategoryList = new ArrayList<>();
        iterableUsers.forEach(subCategoryList::add);

        return subCategoryList;
    }

    public SubCategory getByName(String name) {
        Optional<SubCategory> optionalSubCategory = this.subCategoryRepository.findByName(name);

        if (optionalSubCategory.isPresent()) {
            return optionalSubCategory.get();
        }

        throw new NotFoundException("Sub category does not exists");
    }

    //TODO change all string to lower case categories, sub, jobs name etc on verifying algo assim
    public void updateSubCategory(Long id, String newName, Long newCategoryId) {
        Optional<SubCategory> optionalSubCategory = this.subCategoryRepository.findById(id);

        if (optionalSubCategory.isPresent()) {
            SubCategory subCategoryToUpdate = optionalSubCategory.get();

            if (Objects.nonNull(newName)) {
                Optional<SubCategory> optionalSubCategory1 = this.subCategoryRepository.findByName(newName);

                if (optionalSubCategory1.isPresent()) {
                    SubCategory subCategoryWithSameName = optionalSubCategory1.get();

                    if ( !Objects.equals(id, subCategoryWithSameName.getId())
                        && Objects.nonNull(newCategoryId)
                        && Objects.equals(newCategoryId, subCategoryWithSameName.getCategory().getId())
                    )
                        throw new IllegalArgumentException("Sub category already exists in this category");
                    else if ( !Objects.equals(id, subCategoryWithSameName.getId()))
                        throw new IllegalArgumentException("Sub category already exists!");
                }

                subCategoryToUpdate.setName(newName);
            }

            if (Objects.nonNull(newCategoryId)) {
                Optional<Category> optionalCategory = this.categoryRepository.findById(newCategoryId);

                if (optionalCategory.isEmpty()) throw new IllegalArgumentException("Invalid category!");

                subCategoryToUpdate.setCategory(optionalCategory.get());

            }

            this.subCategoryRepository.save(subCategoryToUpdate);
            return;
        }
        throw new NotFoundException("Sub category does not exists!");

    }

    public void deleteSubCategory(Long id) {
        if (this.subCategoryRepository.existsById(id)) {
            this.subCategoryRepository.deleteById(id);
        }
    }

}

package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.IllegalUserArgumentException;
import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.JobCategory;
import com.fatec.tcc.agendify.Entities.SubCategory;
import com.fatec.tcc.agendify.Entities.RequestTemplate.NameAndIdBody;
import com.fatec.tcc.agendify.Repositories.JobCategoryRepository;
import com.fatec.tcc.agendify.Repositories.SubCategoryRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobCategoryService {
    @Autowired
    private JobCategoryRepository jobCategoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public void createJob(NameAndIdBody nameAndIdBody) {
        if (Strings.trimToNull(nameAndIdBody.getName()) == null
                || nameAndIdBody.getName().isEmpty()
                || nameAndIdBody.getName().isBlank()) {
            throw new IllegalUserArgumentException("Job name is required");
        }

        if (Objects.isNull(nameAndIdBody.getId())) {
            throw new IllegalUserArgumentException("Sub category is required");
        }
        String jobName = nameAndIdBody.getName();
        Long subcategoryId = nameAndIdBody.getId();

        Optional<SubCategory> optionalSubCategory = this.subCategoryRepository.findById(subcategoryId);
        Optional<JobCategory> optionalJob = this.jobCategoryRepository.findByName(jobName);

        if (optionalSubCategory.isEmpty()) throw new IllegalArgumentException("Invalid sub category!");

        SubCategory subCategory = optionalSubCategory.get();

        if (optionalJob.isEmpty()) {
            this.jobCategoryRepository.save(new JobCategory(nameAndIdBody.getName(), subCategory));
            return;
        }

        throw new IllegalArgumentException("Job name is already registered");
    }

    public List<JobCategory> getAll(){
        Iterable<JobCategory> iterableUsers = this.jobCategoryRepository.findAll();
        List<JobCategory> jobCategoryList = new ArrayList<>();
        iterableUsers.forEach(jobCategoryList::add);

        return jobCategoryList;
    }

    public List<JobCategory> getAllJobCategoriesBySubcategory(Long subCategoryId){
        return this.jobCategoryRepository.findAllBySubCategory_Id(subCategoryId);
    }

    public JobCategory getById(Long id) {
        Optional<JobCategory> optionalJob = this.jobCategoryRepository.findById(id);

        if (optionalJob.isPresent()) {
            return optionalJob.get();
        }

        throw new NotFoundException("Job does not exists");
    }

    public JobCategory getByName(String name) {
        Optional<JobCategory> optionalJob = this.jobCategoryRepository.findByName(name);

        if (optionalJob.isPresent()) {
            return optionalJob.get();
        }

        throw new NotFoundException("Job does not exists");
    }

    public void updateJob(Long id, String newName, Long newSubCategoryId) {
        Optional<JobCategory> optionalJob = this.jobCategoryRepository.findById(id);

        if (optionalJob.isPresent()) {
            JobCategory jobCategoryToUpdate = optionalJob.get();

            if (Objects.nonNull(newName)) {
                Optional<JobCategory> optionalJob1 = this.jobCategoryRepository.findByName(newName);

                if (optionalJob1.isPresent()) {
                    JobCategory jobCategoryWithSameName = optionalJob1.get();

                    if ( !Objects.equals(id, jobCategoryWithSameName.getId())
                            && Objects.nonNull(newSubCategoryId)
                            && Objects.equals(newSubCategoryId, jobCategoryWithSameName.getSubCategory().getId())
                    )
                        throw new IllegalArgumentException("Job already exists in this category");
                    else if ( !Objects.equals(id, jobCategoryWithSameName.getId()))
                        throw new IllegalArgumentException("Job already exists!");
                }

                jobCategoryToUpdate.setName(newName);
            }

            if (Objects.nonNull(newSubCategoryId)) {
                Optional<SubCategory> optionalSubCategory = this.subCategoryRepository.findById(newSubCategoryId);

                if (optionalSubCategory.isEmpty()) throw new IllegalArgumentException("Invalid sub category!");

                jobCategoryToUpdate.setSubCategory(optionalSubCategory.get());

            }

            this.jobCategoryRepository.save(jobCategoryToUpdate);
            return;
        }
        throw new NotFoundException("Job does not exists!");

    }

    public void deleteJob(Long id) {
        if (this.jobCategoryRepository.existsById(id)) {
            this.jobCategoryRepository.deleteById(id);
        }
    }
}

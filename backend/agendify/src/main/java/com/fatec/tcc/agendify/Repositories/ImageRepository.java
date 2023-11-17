package com.fatec.tcc.agendify.Repositories;

import com.fatec.tcc.agendify.Entities.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {
}
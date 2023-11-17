package com.fatec.tcc.agendify.Services;

import com.fatec.tcc.agendify.CustomExceptions.NotFoundException;
import com.fatec.tcc.agendify.Entities.Image;
import com.fatec.tcc.agendify.Repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;
    public Image saveImage(String base64) {
        Image image = new Image();
        image.setBase64(base64);
        image.setCreatedAt(new Date());

        imageRepository.save(image);

        return image;
    }

    public Image getImage(Long id) throws IOException {
        return imageRepository.findById(id).get();
    }

    public List<String> getImage() throws IOException {
        Iterable<Image> image = imageRepository.findAll();
        List<String> images = new ArrayList<>();

        image.forEach(image1 -> images.add(image1.getBase64()));
        return images;
    }

}

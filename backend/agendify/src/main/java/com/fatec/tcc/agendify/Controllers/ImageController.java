package com.fatec.tcc.agendify.Controllers;

import com.fatec.tcc.agendify.Entities.Image;
import com.fatec.tcc.agendify.Entities.RequestTemplate.ImageRequest;
import com.fatec.tcc.agendify.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/images")
    public ResponseEntity<?> saveImage(@RequestBody ImageRequest request) throws IOException {
        Image image = this.imageService.saveImage(request.base64());
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/images/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable Long id) throws IOException {
        Image image = this.imageService.getImage(id);

        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @GetMapping(value = "/images/")
    public ResponseEntity<?> getImage() throws IOException {
        List<String> images = this.imageService.getImage();

        return new ResponseEntity<>(images, HttpStatus.OK);
    }
}
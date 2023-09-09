package com.fatec.tcc.agendify.Controllers;

import com.fatec.tcc.agendify.Entities.ImageData;
import com.fatec.tcc.agendify.Services.ImageDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/agenda/image")
public class ImageDataController {

    @Autowired
    private ImageDataService imageDataService;

    @PostMapping("/{object}/{id}")
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile file,
            @PathVariable("object") String object,
            @PathVariable("id") Long id
    ) {
        try {
            Long response = imageDataService.uploadImage(file, object, id);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/info/{name}")
    public ResponseEntity<?>  getImageInfoByName(@PathVariable("name") String name){
        try {
            ImageData image = imageDataService.getInfoByImageByName(name);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(image);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

    }

    @GetMapping("/{name}")
    public ResponseEntity<?>  getImageByName(@PathVariable("name") String name){
        try {
            byte[] image = imageDataService.getImage(name);

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf("image/png"))
                    .body(image);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }

    }


}
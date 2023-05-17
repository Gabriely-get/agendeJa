package com.fatec.tcc.agendeja.Services;

import com.fatec.tcc.agendeja.Entities.ImageData;
import com.fatec.tcc.agendeja.Entities.PortfolioJob;
import com.fatec.tcc.agendeja.Entities.User;
import com.fatec.tcc.agendeja.Repositories.ImageDataRepository;
import com.fatec.tcc.agendeja.Repositories.PortfolioJobRepository;
import com.fatec.tcc.agendeja.Repositories.UserRepository;
import com.fatec.tcc.agendeja.Utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
public class ImageDataService {

    @Autowired
    private ImageDataRepository imageDataRepository;

    @Autowired
    private PortfolioJobRepository portfolioJobRepository;

    @Autowired
    private UserRepository userRepository;

    public Long uploadImage(MultipartFile file, String type, Long id) throws IOException {


        if (type.equals("job")) {
            Optional<PortfolioJob> optionalPortfolioJob = this.portfolioJobRepository.findById(id);

            if (optionalPortfolioJob.isEmpty()) throw new RuntimeException("Error! Can not save image, job does not exists!");

            return imageDataRepository.save(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes()))
                    .portfolioJob(optionalPortfolioJob.get()).build()).getId();
        }

        if (type.equals("user")) {
            Optional<User> optionalUser = this.userRepository.findById(id);

            if (optionalUser.isEmpty()) throw new RuntimeException("Error! Can not save image, user does not exists!");

            return imageDataRepository.save(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes()))
                    .build()).getId();
        }

        throw new IllegalArgumentException("Can not upload image, entity type unrecognized");
//        return file.getOriginalFilename();

    }

    public ImageData getInfoByImageByName(String name) throws DataFormatException, IOException {
        Optional<ImageData> dbImage = imageDataRepository.findByName(name);

        return ImageData.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .imageData(ImageUtil.decompressImage(dbImage.get().getImageData())).build();

    }

    public byte[] getImage(String name) throws DataFormatException, IOException {
        Optional<ImageData> dbImage = imageDataRepository.findByName(name);
        byte[] image = ImageUtil.decompressImage(dbImage.get().getImageData());
        return image;
    }


}
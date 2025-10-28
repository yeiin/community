package com.ktb.community.service.image;

import com.ktb.community.dto.image.ImageRequest;
import com.ktb.community.dto.image.ImageResponse;
import com.ktb.community.global.processor.ImageProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static com.ktb.community.global.enums.FileType.ORIGINAL;
import static com.ktb.community.global.enums.FileType.WEBP;

import org.apache.commons.io.FilenameUtils;


@Service
public class LocalImageServiceImpl implements ImageService{

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.image-base-url}")
    private String imageBaseUrl;

    private final ImageProcessor imageProcessor;

    public LocalImageServiceImpl(final ImageProcessor imageProcessor) {
        this.imageProcessor = imageProcessor;
    }

    @Override
    public ImageResponse uploadImage(final ImageRequest imageRequest, final MultipartFile multipartFile)  {
        String uuid = UUID.randomUUID().toString();

        String originalPath = saveOriginalFile(imageRequest, multipartFile, uuid);
        String webPPath = saveWebPFile(imageRequest, multipartFile, uuid);
        return new ImageResponse(webPPath.toString());
    }

    private String saveOriginalFile(final ImageRequest imageRequest, final MultipartFile multipartFile, final String uuid) {
        try {
            Path saveOriginalDir = Paths
                    .get(uploadDir, imageRequest.type().toString().toLowerCase()+"/"+ORIGINAL)
                    .normalize();

            Files.createDirectories(saveOriginalDir);

            String storedName = uuid + "_" + multipartFile.getOriginalFilename();
            Path originalPath = saveOriginalDir.resolve(storedName);

            Files.copy(multipartFile.getInputStream(), originalPath, StandardCopyOption.REPLACE_EXISTING);

            return originalPath.toString();
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String saveWebPFile(final ImageRequest imageRequest, final MultipartFile multipartFile, final String uuid) {
        try {

            String relativePath = imageRequest.type().toString().toLowerCase()+"/"+WEBP;
            Path saveWebPDir = Paths
                    .get(uploadDir, relativePath)
                    .normalize();
            Files.createDirectories(saveWebPDir);

            byte[] webpByte = imageProcessor.convertToWebp(multipartFile);
            String baseName = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());

            String webPStoredName = uuid + "_" + baseName + ".webp";
            Path webPPath = saveWebPDir.resolve(webPStoredName);

            Files.write(webPPath, webpByte);

            return imageBaseUrl+relativePath+"/"+webPStoredName;
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}

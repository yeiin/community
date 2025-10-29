package com.ktb.community.controller.image;

import com.ktb.community.dto.image.ImageRequest;
import com.ktb.community.dto.image.ImageResponse;
import com.ktb.community.service.image.ImageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(final ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("")
    public ImageResponse imageUpload(@RequestPart("file") MultipartFile file,
                                     @RequestPart("imageRequest")ImageRequest imageRequest) {

        return imageService.uploadImage(imageRequest, file);
    }
}

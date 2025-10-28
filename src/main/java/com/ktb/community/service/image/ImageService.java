package com.ktb.community.service.image;

import com.ktb.community.dto.image.ImageRequest;
import com.ktb.community.dto.image.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    ImageResponse uploadImage(final ImageRequest imageRequest, final MultipartFile multipartFile);
}

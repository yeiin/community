package com.ktb.community.global.processor;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.webp.WebpWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageProcessor {

    @Value("${file.upload-dir}")
    private String uploadDir;


    public byte[] convertToWebp(MultipartFile multipartFile) {
        try {
            ImmutableImage image = ImmutableImage.loader().fromStream(multipartFile.getInputStream());
            return image.bytes(WebpWriter.DEFAULT);
        } catch (IOException e) {
            throw new IllegalStateException("WEBP 변환 실패: " + e.getMessage(), e);
        }
    }

    public byte[] convertToWebpWithLossLess(MultipartFile multipartFile) {
        try {
            ImmutableImage image = ImmutableImage.loader().fromStream(multipartFile.getInputStream());
            return image.bytes(WebpWriter.DEFAULT.withLossless());
        } catch (IOException e) {
            throw new IllegalStateException("WEBP 변환 실패: " + e.getMessage(), e);
        }
    }
}

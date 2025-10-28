package com.ktb.community.dto.image;

import com.ktb.community.global.enums.ImageType;

public record ImageRequest(
        ImageType type
) {
}

package com.ktb.community.global.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ImageType {
    PROFILE, POST;

    @JsonCreator
    public static ImageType from(String value) {
        return ImageType.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toValue() {
        return name();
    }
}

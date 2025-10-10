package com.ktb.community.global.validator;

import com.ktb.community.global.annotation.PostTitle;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PostTitleValidator implements ConstraintValidator<PostTitle, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null || s.isBlank()) return false;

        s = s.trim();
        if (s.length()>26) return false;

        return true;
    }
}

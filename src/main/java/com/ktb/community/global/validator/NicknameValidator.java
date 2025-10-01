package com.ktb.community.global.validator;

import com.ktb.community.global.annotation.Nickname;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NicknameValidator implements ConstraintValidator<Nickname, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null || s.isBlank()) return false;

        s = s.trim();
        if (s.contains(" ") || s.length()>10) return false;

        return true;
    }
}

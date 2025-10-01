package com.ktb.community.global.annotation;

import com.ktb.community.global.validator.PasswordValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Constraint(validatedBy = {PasswordValidator.class})
public @interface Password {
    String message() default "비밀번호는 대문자, 소문자, 숫자, 특수문자를 최소 1개 포함해야 하며, 8글자 이상 20글자 이하여야 합니다.";
}

package com.onlinebusreservation.areas.user.annotations;

import com.onlinebusreservation.validators.PasswordMatchingValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchingValidator.class)
public @interface PasswordsMatching {

    String message() default "Passwords are not matching.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.onlinebusreservation.areas.city.entities.annotations;

import com.onlinebusreservation.validators.CityMatchingValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CityMatchingValidator.class)
public @interface CitiesMatching {

    String message() default "City origin cannot be the same with destination city.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

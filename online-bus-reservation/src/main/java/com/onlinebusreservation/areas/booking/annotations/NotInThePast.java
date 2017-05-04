package com.onlinebusreservation.areas.booking.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PresentOrFutureValidator.class)
public @interface NotInThePast {

    String message() default "Wrong Date or date must not be in the past.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

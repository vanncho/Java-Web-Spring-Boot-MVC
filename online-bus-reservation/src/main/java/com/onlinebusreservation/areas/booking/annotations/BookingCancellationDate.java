package com.onlinebusreservation.areas.booking.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BookingCancellationDateValidator.class)
public @interface BookingCancellationDate {

    String message() default "Booking cancellation can be made no latter than 24 hours before travelling.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

package com.residentevil.validators;

import com.residentevil.entities.annotations.NotInThePast;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PresentOrFutureValidator implements ConstraintValidator<NotInThePast, String> {

    @Override
    public void initialize(NotInThePast notInThePast) {

    }

    @Override
    public boolean isValid(String inputDate, ConstraintValidatorContext constraintValidatorContext) {

        DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        Date date = null;

        try {
            date = format.parse(inputDate);
            Date now = new Date();

            if (date.after(now)) {

                return true;
            }

        } catch (ParseException e) {

            return true;
        }

        return false;
    }
}

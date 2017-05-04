package com.onlinebusreservation.areas.booking.annotations;

import com.onlinebusreservation.constants.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class PresentOrFutureValidator implements ConstraintValidator<NotInThePast, String> {

    @Override
    public void initialize(NotInThePast notInThePast) {

    }

    @Override
    public boolean isValid(String inputDate, ConstraintValidatorContext constraintValidatorContext) {

        SimpleDateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
        Date date = null;

        LocalDate localDate = LocalDate.now();
        String today = String.valueOf(localDate);
        Date now = null;

        try {
            date = format.parse(inputDate);
            now = format.parse(today);

            if (date.after(now)) {

                return true;
            }

        } catch (ParseException e) {

            return false;
        }

        return false;
    }
}

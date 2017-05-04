package com.onlinebusreservation.areas.booking.annotations;

import com.onlinebusreservation.areas.booking.models.view.BookingCancellationViewModel;
import com.onlinebusreservation.constants.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BookingCancellationDateValidator implements ConstraintValidator<BookingCancellationDate, Object> {

   public void initialize(BookingCancellationDate constraint) {
   }

   public boolean isValid(Object object, ConstraintValidatorContext context) {

      BookingCancellationViewModel booking = (BookingCancellationViewModel) object;

      DateFormat format = new SimpleDateFormat(Constants.DATE_FORMAT);
      LocalDate localDate = LocalDate.now();
      String today = String.valueOf(localDate);
      String dateOfJourneyString = booking.getDateOfJourney();

      Date now = null;
      Date dateOfJourney = null;

      try {
         now = format.parse(today);
         dateOfJourney = format.parse(dateOfJourneyString);

      } catch (ParseException e) {
         e.printStackTrace();
      }

      long duration  = dateOfJourney.getTime() - now.getTime();

      long diffInHours = TimeUnit.MILLISECONDS.toHours(duration);

      return diffInHours > 24;
   }
}

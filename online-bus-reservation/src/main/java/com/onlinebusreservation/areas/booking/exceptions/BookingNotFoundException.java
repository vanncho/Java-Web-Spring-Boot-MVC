package com.onlinebusreservation.areas.booking.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Booking with this id does not exist.")
public class BookingNotFoundException extends RuntimeException {
}

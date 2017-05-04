package com.onlinebusreservation.areas.bus.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Bus with this id does not exist.")
public class BusNotFoundException extends RuntimeException {

}

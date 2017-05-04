package com.onlinebusreservation.areas.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with this id not exist.")
public class UserNotFoundException extends RuntimeException {
}

package com.onlinebusreservation.constants;

public interface RegexPatterns {

    String REGEX_EMAIL = "^[a-zA-Z0-9_!#$%&'*+\\/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

    String REGEX_FULL_NAME = "^[\\p{L} .'-]+$";

    String REGEX_MOBILE_NUMBER = "^[0-9]{10}$";
}

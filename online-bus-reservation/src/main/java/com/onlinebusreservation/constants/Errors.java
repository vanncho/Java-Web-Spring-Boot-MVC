package com.onlinebusreservation.constants;

public interface Errors {

    //region USER

    String WRONG_USERNAME_OR_PASSWORD = "Oops! Invalid username or password.";

    String USERNAME_LENGTH = "Username must be at least 5 symbols long.";

    String USER_FULL_NAME_LENGTH = "Full Name must be at least 5 symbols long";

    String USER_FULL_NAME = "Enter valid full name.";

    String USER_EXIST = "User with this username already exist!";

    String USER_EMAIL = "Please enter a valid email.";

    String USER_MOBILE_NUMBER = "Invalid mobile number.";

    String USER_PASSWORD_LENGTH = "Password must be at least 6 symbols long.";

    String EXISTING_EMAIL = "User with this email already exist!";

    //endregion

    //region BUS

    String BUS_NAME = "Please enter bus name.";

    String BUS_NAME_LENGTH = "Bus name must be at least 5 symbols long.";

    String BUS_ORIGIN = "Please select bus start origin.";

    String BUS_DESTINATION = "Please select bus destination to.";

    String BUS_START_HOUR = "Please enter start hour.";

    String BUS_INVALID_HOUR = "Invalid hour range.";

    String BUS_START_MINUTES = "Please enter start minutes.";

    String BUS_INVALID_MINUTES = "Invalid minutes range.";

    String BUS_SEATS_NUMBER = "Please specify bus seats number.";

    String BUS_TICKET_PRICE = "Please specify ticket price.";

    //endregion

    //region BOOKING

    String BOOKING_DATE_OF_JOURNEY = "Select date.";

    String BOOKING_SEAT_SELECT = "Please select at least one seat!";

    //endregion

    //region FEEDBACK

    String NO_FEEDBACK = "You forgot to write feedback.";

    String FEEDBACK_LENGTH = "Your feedback must be at least 20 symbols long.";

    //endregion

    //region COMPANY INFO

    String COMPANY_NAME = "Please enter company name.";

    String COMPANY_NAME_LENGTH = "Company name must be at least 5 symbols long.";

    String TOWN_NAME = "Please enter town name.";

    String TOWN_NAME_LENGTH = "Town name must be at least 3 symbols long.";

    String COMPANY_ADDRESS = "Please enter company address.";

    String COMPANY_ADDRESS_LENGTH = "Company address must be at least 10 symbols long.";

    String COMPANY_TELEPHONE = "Please enter phone number.";

    //endregion
}

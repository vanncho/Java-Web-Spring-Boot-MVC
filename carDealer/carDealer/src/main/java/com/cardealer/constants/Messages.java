package com.cardealer.constants;

public interface Messages {

    String DELETE_WARNING = "Do you want to delete %s?";

    //region USER

    String EXISTING_USER = "Oh snap! User with this email already exists.";

    String NON_EXISTING_USER = "Oh snap! User with this email and password not exists.";

    String WRONG_EMAIL = "Oh snap! Invalid email it must contain @ sign and a period.";

    String WRONG_PASSWORD = "Oh snap! Password length must be at least 6 symbols and must contain at least 1 uppercase, 1 lowercase letter and 1 digit";

    String MISMATCH_PASSWORDS = "Oh snap! Passwords are not matching.";

    //endregion
}

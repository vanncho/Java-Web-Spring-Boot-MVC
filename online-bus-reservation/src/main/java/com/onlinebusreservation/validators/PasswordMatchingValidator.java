package com.onlinebusreservation.validators;

import com.onlinebusreservation.areas.user.annotations.PasswordsMatching;
import com.onlinebusreservation.areas.user.model.binding.UserPasswordMatcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchingValidator implements ConstraintValidator<PasswordsMatching, Object> {

   @Override
   public void initialize(PasswordsMatching constraint) {
   }

   @Override
   public boolean isValid(Object model, ConstraintValidatorContext context) {

      UserPasswordMatcher userModel = (UserPasswordMatcher) model;

      if (userModel.getPassword().equals(userModel.getConfirmPassword())) {

         return true;
      }

      return false;
   }
}

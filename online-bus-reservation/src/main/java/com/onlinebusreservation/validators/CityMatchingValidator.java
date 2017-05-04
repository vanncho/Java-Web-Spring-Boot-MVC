package com.onlinebusreservation.validators;

import com.onlinebusreservation.areas.city.entities.annotations.CitiesMatching;
import com.onlinebusreservation.areas.bus.models.binding.AddBusModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CityMatchingValidator implements ConstraintValidator<CitiesMatching, Object> {

    @Override
    public void initialize(CitiesMatching citiesMatching) {

    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {

        AddBusModel addBusModel = (AddBusModel) object;
        String originCity = addBusModel.getOriginatedFromName();
        String destinationCity = addBusModel.getDestinationToName();

        if (originCity.equals(destinationCity)) {
            return false;
        }

        return true;
    }
}

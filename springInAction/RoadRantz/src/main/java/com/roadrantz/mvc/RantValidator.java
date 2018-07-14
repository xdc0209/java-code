package com.roadrantz.mvc;

import org.apache.oro.text.perl.Perl5Util;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.roadrantz.domain.Rant;

public class RantValidator implements Validator
{
    public boolean supports(Class clazz)
    {
        return clazz.equals(Rant.class);
    }

    public void validate(Object command, Errors errors)
    {
        Rant rant = (Rant) command;

        ValidationUtils.rejectIfEmpty(errors, "vehicle.state", "required.state", "State is required.");

        ValidationUtils.rejectIfEmpty(errors, "vehicle.plateNumber", "required.plateNumber",
                "The license plate number is required.");

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rantText", "required.rantText",
                "You must enter some rant text.");

        validatePlateNumber(rant.getVehicle().getPlateNumber(), errors);
    }

    private static final String PLATE_REGEXP = "/[a-z0-9]{2,6}/i";

    private void validatePlateNumber(String plateNumber, Errors errors)
    {
        Perl5Util perl5Util = new Perl5Util();
        if (!perl5Util.match(PLATE_REGEXP, plateNumber))
        {
            errors.reject("invalid.plateNumber", "Invalid license plate number.");
        }
    }
}

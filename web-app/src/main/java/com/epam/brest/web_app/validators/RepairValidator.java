package com.epam.brest.web_app.validators;

import com.epam.brest.model.Repair;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.model.constants.RepairConstants.ADDRESS_SIZE;
/**
 * Repair validator.
 */

@Component
public class RepairValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Repair.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "address", "address.empty");
        Repair repair = (Repair) target;

        if (StringUtils.hasLength(repair.getAddress())
                && repair.getAddress().length() > ADDRESS_SIZE) {
            errors.rejectValue("address", "address.maxSize");
        }

    }
}

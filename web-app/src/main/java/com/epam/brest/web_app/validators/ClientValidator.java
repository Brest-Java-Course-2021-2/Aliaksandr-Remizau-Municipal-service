package com.epam.brest.web_app.validators;

import com.epam.brest.model.Client;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static com.epam.brest.model.constants.ClientConstants.CLIENT_NAME_SIZE;
/**
 * Client validator.
 */
@Component
public class ClientValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "clientName", "clientName.empty");
        Client client = (Client) target;

        if (StringUtils.hasLength(client.getClientName())
                && client.getClientName().length() > CLIENT_NAME_SIZE) {
            errors.rejectValue("clientName", "clientName.maxSize");
        }
    }
}

package vaudoise.insurance.validator;

import vaudoise.insurance.model.model.ClientDtos;
import vaudoise.insurance.validator.client.ValidClient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ClientTypeValidator implements ConstraintValidator<ValidClient, ClientDtos.ClientResponse> {

    @Override
    public boolean isValid(ClientDtos.ClientResponse client, ConstraintValidatorContext context) {
        if (client.type() == null) return true; // @Pattern handles null separately

        if ("PERSON".equals(client.type())) {
            // PERSON → companyIdentifier must be null or empty
            if (client.companyIdentifier() != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "companyIdentifier must be null for PERSON"
                ).addPropertyNode("companyIdentifier").addConstraintViolation();
                return false;
            }
        } else if ("COMPANY".equals(client.type())) {
            // COMPANY → birthDate must be null
            if (client.birthDate() != null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(
                        "birthDate must be null for COMPANY"
                ).addPropertyNode("birthDate").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}
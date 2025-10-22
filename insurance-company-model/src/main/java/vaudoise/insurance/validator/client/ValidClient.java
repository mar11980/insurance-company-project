package vaudoise.insurance.validator.client;

import vaudoise.insurance.validator.ClientTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ClientTypeValidator.class)
@Documented
public @interface ValidClient {
    String message() default "Invalid client data";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

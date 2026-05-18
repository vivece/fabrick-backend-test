package it.orbyta.fabrick.dto.custom_validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidatorMoneyTransferRequestImpl.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorMoneyTransferRequest {
    String message() default "invalid money transfer request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

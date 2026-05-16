package it.orbyta.fabrick.dto.custom_validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidatorAccountImpl.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorAccount {
    String message() default "invalid account";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

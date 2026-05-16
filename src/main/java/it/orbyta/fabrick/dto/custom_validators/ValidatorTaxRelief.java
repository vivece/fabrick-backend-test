package it.orbyta.fabrick.dto.custom_validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidatorTaxReliefImpl.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatorTaxRelief {
    String message() default "invalid taxRelief";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

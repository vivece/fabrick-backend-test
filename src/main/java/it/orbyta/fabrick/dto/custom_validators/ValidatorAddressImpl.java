package it.orbyta.fabrick.dto.custom_validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidatorAddressImpl implements ConstraintValidator<ValidatorAddress, String> {

    private static final Set<String> VALID_CODES = Arrays.stream(Locale.getISOCountries())
            .collect(Collectors.toSet());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return VALID_CODES.contains(value.toUpperCase());
    }
}

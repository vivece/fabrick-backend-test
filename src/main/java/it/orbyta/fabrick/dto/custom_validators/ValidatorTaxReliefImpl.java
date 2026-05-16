package it.orbyta.fabrick.dto.custom_validators;

import it.orbyta.fabrick.dto.request.moneyTransfer.TaxRelief;
import it.orbyta.fabrick.dto.request.moneyTransfer.enumerations.TaxReliefType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidatorTaxReliefImpl implements ConstraintValidator<ValidatorTaxRelief, TaxRelief> {

    @Override
    public boolean isValid(TaxRelief taxRelief, ConstraintValidatorContext context) {
        if (taxRelief == null) {
            return true;
        }

        List<String> taxReliefTypeCodes = Arrays.stream(TaxReliefType.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        boolean isValidTaxReliefId = taxReliefTypeCodes.contains(taxRelief.getTaxReliefId());
        if (!isValidTaxReliefId) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("taxReliefId must be one of: " + String.join(",", taxReliefTypeCodes))
                    .addPropertyNode("taxReliefId")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

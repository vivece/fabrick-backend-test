package it.orbyta.fabrick.dto.custom_validators;


import it.orbyta.fabrick.dto.request.moneyTransfer.MoneyTransferRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidatorMoneyTransferRequestImpl implements ConstraintValidator<ValidatorMoneyTransferRequest, MoneyTransferRequest> {
    @Override
    public boolean isValid(MoneyTransferRequest request, ConstraintValidatorContext context) {
        if(!request.getIsInstant() && request.getExecutionDate() == null) {
            return false;
        }
        return true;
    }
}

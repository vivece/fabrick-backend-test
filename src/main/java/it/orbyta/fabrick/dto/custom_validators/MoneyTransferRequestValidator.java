package it.orbyta.fabrick.dto.custom_validators;


import it.orbyta.fabrick.dto.request.moneyTransfer.MoneyTransferRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class MoneyTransferRequestValidator implements ConstraintValidator<ValidMoneyTransferRequest, MoneyTransferRequest> {

    @Override
    public boolean isValid(MoneyTransferRequest request, ConstraintValidatorContext context) {
        return true;
    }



}

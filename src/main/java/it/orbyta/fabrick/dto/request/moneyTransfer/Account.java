package it.orbyta.fabrick.dto.request.moneyTransfer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Account {

    @NotBlank(message = "creditor.account.accountCode is required")
    private String accountCode;

    //TODO implementa un customValidator (Constraint Validator?) per la specifica "The BIC code of the creditor bank. Required if a SWIFT account number is provided as accountCode. Optional if an IBAN code is provided as accountCode."
    private String bicCode;

}

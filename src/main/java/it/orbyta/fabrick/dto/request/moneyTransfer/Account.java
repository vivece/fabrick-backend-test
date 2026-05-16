package it.orbyta.fabrick.dto.request.moneyTransfer;

import it.orbyta.fabrick.dto.custom_validators.ValidatorAccount;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ValidatorAccount(message = "bicCode is required when accountCode is an not an Iban")
public class Account {
    @NotBlank(message = "creditor.account.accountCode is required")
    private String accountCode;

    private String bicCode;
}

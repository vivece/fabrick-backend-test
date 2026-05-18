package it.orbyta.fabrick.dto.request.moneyTransfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import it.orbyta.fabrick.dto.custom_validators.ValidatorAccount;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ValidatorAccount(message = "bicCode is required when accountCode is not an IBAN")
public class Account {
    @NotBlank(message = "creditor.account.accountCode is required")
    private String accountCode;

    private String bicCode;
}

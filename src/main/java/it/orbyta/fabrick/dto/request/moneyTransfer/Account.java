package it.orbyta.fabrick.dto.request.moneyTransfer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Account {
    private String accountCode;
    private String bicCode;
}

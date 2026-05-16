package it.orbyta.fabrick.dto.response.moneyTransfer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class Account {
    private String accountCode;
    private String bicCode;
}

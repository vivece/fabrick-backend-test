package it.orbyta.fabrick.dto.request.moneyTransfer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class Creditor {
    private String name;
    private Account account;
    private Address address;
}

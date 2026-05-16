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

    @NotBlank(message = "creditor.name is required")
    @Size(max = 70, message = "creditor.name must be at most 70 characters")
    private String name;

    @Valid
    @NotNull(message = "creditor.account is required")
    private Account account;

    @Valid
    private Address address;
}

package it.orbyta.fabrick.dto.response.moneyTransfer;

import it.orbyta.fabrick.dto.request.moneyTransfer.Account;
import it.orbyta.fabrick.dto.request.moneyTransfer.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Creditor {
    private String name;
    private Account account;
    private Address address;
}

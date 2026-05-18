package it.orbyta.fabrick.dto.response.moneyTransfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Creditor {
    private String name;
    private Account account;
    private Address address;
}

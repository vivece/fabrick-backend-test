package it.orbyta.fabrick.dto.response.moneyTransfer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Debtor {
    private String name;
    private Account account;
}

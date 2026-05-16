package it.orbyta.fabrick.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BalanceResponse {

    private LocalDate date;
    private BigDecimal balance;
    private BigDecimal availableBalance;
    private String currency;

}

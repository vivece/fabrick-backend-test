package it.orbyta.fabrick.dto.response.moneyTransfer;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class Amount {
    private BigDecimal debtorAmount;
    private String debtorCurrency;
    private BigDecimal creditorAmount;
    private String creditorCurrency;
    private LocalDate creditorCurrencyDate;
    private BigDecimal exchangeRate;
}

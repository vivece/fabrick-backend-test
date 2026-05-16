package it.orbyta.fabrick.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Amount {
    private BigDecimal debtorAmount;
    private String debtorCurrency;
    private BigDecimal creditorAmount;
    private String creditorCurrency;
    private LocalDate creditorCurrencyDate;
    private BigDecimal exchangeRate;
}

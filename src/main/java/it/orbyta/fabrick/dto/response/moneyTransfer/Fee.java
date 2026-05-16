package it.orbyta.fabrick.dto.response.moneyTransfer;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Fee {
    private String feeCode;
    private String description;
    private BigDecimal amount;
    private String currency;
}

package it.orbyta.fabrick.dto.response.transactions;

import it.orbyta.fabrick.dto.response.TransactionInfo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    private String transactionId;
    private String operationId;
    private LocalDate accountingDate;
    private LocalDate valueDate;
    private TransactionInfo transactionInfo;
    private BigDecimal amount;
    private String currency;
    private String description;

}

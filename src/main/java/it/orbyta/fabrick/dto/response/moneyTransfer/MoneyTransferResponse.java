package it.orbyta.fabrick.dto.response.moneyTransfer;

import it.orbyta.fabrick.dto.request.moneyTransfer.Creditor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MoneyTransferResponse {

    private String moneyTransferId;
    private String status;
    private String direction;
    private Creditor creditor;
    private Debtor debtor;
    private String cro;
    private String trn;
    private String uri;
    private String description;
    private LocalDate createdTime;
    private LocalDate accountedTime;
    private LocalDate debtorValueDate;
    private LocalDate creditorValueDate;
    private Amount amount;
    private Boolean isUrgent;
    private Boolean isInstant;
    private String feeType;
    private String feeAccountId;
    private Boolean hasTaxRelief;
    private List<Fee> fees;

}

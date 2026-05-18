package it.orbyta.fabrick.dto.request.moneyTransfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import it.orbyta.fabrick.dto.custom_validators.ValidatorMoneyTransferRequest;
import it.orbyta.fabrick.dto.enumerations.FeeType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ValidatorMoneyTransferRequest(message = "executionDate is required when isInstant is false")
public class MoneyTransferRequest {

    @Valid
    @NotNull(message = "creditor is required")
    private Creditor creditor;

    @NotNull(message = "executionDate is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate executionDate;

    @NotBlank(message = "description is required")
    @Size(max = 140, message = "description must not exceed 140 characters")
    private String description;

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0.01", message = "amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "currency is required")
    @Size(min = 3, max = 3, message = "currency must be a 3-letter ISO code")
    private String currency;

    @Valid
    private TaxRelief taxRelief;

    private Boolean isUrgent = Boolean.FALSE;
    private Boolean isInstant = Boolean.FALSE;
    private String feeType = String.valueOf(FeeType.SHA);
    private String feeAccountId;

}

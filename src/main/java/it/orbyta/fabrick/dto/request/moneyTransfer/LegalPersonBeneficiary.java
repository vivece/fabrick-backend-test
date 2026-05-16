package it.orbyta.fabrick.dto.request.moneyTransfer;

import lombok.*;
import javax.validation.constraints.*;

@Getter
@Setter
public class LegalPersonBeneficiary {

    @NotBlank
    private String fiscalCode;

    private String legalRepresentativeFiscalCode;
}
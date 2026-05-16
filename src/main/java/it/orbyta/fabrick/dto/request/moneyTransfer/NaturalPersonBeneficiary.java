package it.orbyta.fabrick.dto.request.moneyTransfer;

import lombok.*;
import javax.validation.constraints.*;

@Getter
@Setter
public class NaturalPersonBeneficiary {
    @NotBlank
    private String fiscalCodeField1;
    private String fiscalCodeField2;
    private String fiscalCodeField3;
    private String fiscalCodeField4;
    private String fiscalCodeField5;
}

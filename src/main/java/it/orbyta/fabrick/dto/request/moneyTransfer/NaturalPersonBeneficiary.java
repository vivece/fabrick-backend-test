package it.orbyta.fabrick.dto.request.moneyTransfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NaturalPersonBeneficiary {
    @NotBlank
    private String fiscalCodeField1;
    private String fiscalCodeField2;
    private String fiscalCodeField3;
    private String fiscalCodeField4;
    private String fiscalCodeField5;
}

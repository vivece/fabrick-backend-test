package it.orbyta.fabrick.dto.request.moneyTransfer;

import it.orbyta.fabrick.dto.custom_validators.ValidatorTaxRelief;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@ValidatorTaxRelief
public class TaxRelief {

    private String taxReliefId;

    @NotNull
    private Boolean isCondoUpgrade;

    @NotBlank
    private String creditorFiscalCode;

    @NotBlank
    @Pattern(regexp = "NATURAL_PERSON|LEGAL_PERSON",
            message = "beneficiaryType must be one of: NATURAL_PERSON, LEGAL_PERSON")
    private String beneficiaryType;

    private NaturalPersonBeneficiary naturalPersonBeneficiary;
    private LegalPersonBeneficiary legalPersonBeneficiary;

}

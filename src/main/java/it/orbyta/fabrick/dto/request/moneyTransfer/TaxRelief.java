package it.orbyta.fabrick.dto.request.moneyTransfer;

import it.orbyta.fabrick.dto.request.moneyTransfer.enumerations.BeneficiaryType;
import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class TaxRelief {

    @Pattern(regexp = "119R|DL50|L296|L449|L234") //TODO The ID of the tax relief. Valid values: 119R	Interventi superbonus. e vedi documentazione
    private String taxReliefId;

    @NotNull
    private Boolean isCondoUpgrade;

    @NotBlank
    private String creditorFiscalCode;

    @NotBlank
    private String beneficiaryType; //TODO implementare il controllo

    private NaturalPersonBeneficiary naturalPersonBeneficiary;
    private LegalPersonBeneficiary legalPersonBeneficiary; //TODO implementare il controllo - vedi specifica

    @AssertTrue(message = "beneficiaryType must be NATURAL_PERSON or LEGAL_PERSON")
    public boolean isBeneficiaryTypeValid() {
        try {
            BeneficiaryType.valueOf(beneficiaryType);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

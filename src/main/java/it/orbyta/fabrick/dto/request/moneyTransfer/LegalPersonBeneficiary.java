package it.orbyta.fabrick.dto.request.moneyTransfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LegalPersonBeneficiary {

    @NotBlank
    @Pattern(regexp = "[0-9]{11}", message = "fiscalCode must be 11 digits -ITA PARTITA IVA")
    private String fiscalCode;

    @Pattern(regexp = "[A-Z]{6}[0-9]{2}[ABCDEHLMPRST][0-9LMNPQRSTUV]{2}[A-Z][0-9LMNPQRSTUV]{3}[A-Z]",
            message = "legalPersonBeneficiary.legalRepresentativeFiscalCode must be a valid Italian Codice Fiscale")
    private String legalRepresentativeFiscalCode;
}
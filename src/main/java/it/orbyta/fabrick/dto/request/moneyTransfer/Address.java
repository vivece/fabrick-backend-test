package it.orbyta.fabrick.dto.request.moneyTransfer;

import it.orbyta.fabrick.dto.custom_validators.ValidatorAddress;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@ValidatorAddress(message = "The country code of the creditor must be compliant to ISO")
public class Address {

    private String city;
    @Size(max = 40, message = "creditor.address.address must be at most 40 chars")
    private String address;
    private String countryCode;

}

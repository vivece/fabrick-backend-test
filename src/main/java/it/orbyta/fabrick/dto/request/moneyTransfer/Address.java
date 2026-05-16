package it.orbyta.fabrick.dto.request.moneyTransfer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class Address {

    private String city;
    @Size(max = 40, message = "creditor.address.address must be at most 40 chars")
    private String address;
    //TODO implementare custom validator "The country code of the creditor, compliant to ISO 3166-1 alpha 2 standard."
    private String countryCode;

}

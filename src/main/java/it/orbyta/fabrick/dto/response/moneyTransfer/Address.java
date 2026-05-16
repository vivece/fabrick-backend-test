package it.orbyta.fabrick.dto.response.moneyTransfer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class Address {
    private String city;
    private String address;
    private String countryCode;
}

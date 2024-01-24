package com.rdi.geegstar.dto.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class AddressRequest {
    private Long buildingNumber;
    private String street;
    private String city;
    private String state;
    private String country;
}

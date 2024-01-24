package com.rdi.geegstar.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class BookingResponseAddressResponse {
    private Long buildingNumber;
    private String street;
    private String city;
    private String state;
    private String country;
}

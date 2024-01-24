package com.rdi.geegstar.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AcceptBookingRequest {
    private Long bookingId;
    private Long talentId;
}

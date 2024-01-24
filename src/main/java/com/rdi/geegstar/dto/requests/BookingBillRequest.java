package com.rdi.geegstar.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class BookingBillRequest {
    private Long bookingId;
    private Long talentId;
    private Long plannerId;
    private BigDecimal bookingCost;
    private String text;
}

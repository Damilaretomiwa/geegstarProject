package com.rdi.geegstar.dto.requests;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class PayBookingBillRequest {
    private Long senderId;
    private Long receiverId;
    private Long bookingBillId;
}

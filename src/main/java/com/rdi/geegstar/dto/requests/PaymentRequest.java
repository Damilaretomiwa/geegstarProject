package com.rdi.geegstar.dto.requests;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class PaymentRequest {
    private Long sender;
    private Long receiver;
    private BigDecimal amount;
}

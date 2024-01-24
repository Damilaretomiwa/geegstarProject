package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Payment;
import com.rdi.geegstar.dto.requests.PaymentRequest;
import com.rdi.geegstar.exceptions.UserNotFoundException;

public interface PaymentService {
    Payment pay(PaymentRequest paymentRequest) throws UserNotFoundException;
}

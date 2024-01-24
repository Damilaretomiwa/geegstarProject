package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.Payment;
import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.data.repositories.PaymentRepository;
import com.rdi.geegstar.dto.requests.PaymentRequest;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.PaymentService;
import com.rdi.geegstar.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeegStarPaymentService implements PaymentService {

    private final UserService userService;
    private final PaymentRepository paymentRepository;

    @Override
    public Payment pay(PaymentRequest paymentRequest) throws UserNotFoundException {
        User sender = userService.findUserById(paymentRequest.getSender());
        User receiver = userService.findUserById(paymentRequest.getReceiver());
        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setSender(sender);
        payment.setReceiver(receiver);
        return paymentRepository.save(payment);
    }
}

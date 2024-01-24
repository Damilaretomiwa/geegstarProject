package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Payment;
import com.rdi.geegstar.dto.requests.PaymentRequest;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Test
    @Sql("/db/insertUsers.sql")
    public void testPayment() throws UserNotFoundException {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setSender(103L);
        paymentRequest.setReceiver(102L);
        paymentRequest.setAmount(BigDecimal.valueOf(200_000));

        Payment payment = paymentService.pay(paymentRequest);

        assertThat(payment).isNotNull();
    }
}

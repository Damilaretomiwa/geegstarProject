package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

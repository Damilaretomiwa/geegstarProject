package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.BookingBill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingBillRepository extends JpaRepository<BookingBill, Long> {
}

package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.data.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByTalent(User talent, Pageable pageable);

    Page<Booking> findAllByPlanner(User planer, Pageable pageable);
}

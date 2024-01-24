package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalenderRepository extends JpaRepository<Calendar, Long> {
}

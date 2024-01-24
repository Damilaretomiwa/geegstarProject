package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.EventDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventDetailRepository extends JpaRepository<EventDetail, Long> {
}

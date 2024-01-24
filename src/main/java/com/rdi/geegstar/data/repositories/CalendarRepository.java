package com.rdi.geegstar.data.repositories;

import com.rdi.geegstar.data.models.Calendar;
import com.rdi.geegstar.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    @Query("SELECT c FROM Calendars c WHERE c.eventDateAndTime >=CURRENT_DATE AND c.talent = :talent")
    List<Calendar> findByTalent(User talent);
}

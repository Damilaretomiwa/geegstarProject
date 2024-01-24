package com.rdi.geegstar.services;

import com.rdi.geegstar.dto.response.GetTalentCalendars;
import com.rdi.geegstar.exceptions.UserNotFoundException;

import java.util.List;

public interface CalendarService {
    List<GetTalentCalendars> getTalentCalendars(Long talentId) throws UserNotFoundException;
}

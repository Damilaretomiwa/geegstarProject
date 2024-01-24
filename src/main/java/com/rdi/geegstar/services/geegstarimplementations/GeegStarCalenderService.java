package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.Calendar;
import com.rdi.geegstar.data.models.User;
import com.rdi.geegstar.data.repositories.CalendarRepository;
import com.rdi.geegstar.dto.response.GetTalentCalendars;

import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.CalendarService;
import com.rdi.geegstar.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeegStarCalenderService implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public List<GetTalentCalendars> getTalentCalendars(Long talentId) throws UserNotFoundException {
        User talent = userService.findUserById(talentId);
        List<Calendar> calendars = calendarRepository.findByTalent(talent);
        return calendars.stream()
                .map(calender -> modelMapper.map(calender, GetTalentCalendars.class))
                .toList();
    }
}

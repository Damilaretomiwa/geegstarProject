package com.rdi.geegstar.services.geegstarimplementations;

import com.rdi.geegstar.data.models.*;
import com.rdi.geegstar.data.repositories.BookingRepository;
import com.rdi.geegstar.dto.requests.*;
import com.rdi.geegstar.dto.response.*;
import com.rdi.geegstar.enums.Role;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;
import com.rdi.geegstar.services.BookingService;
import com.rdi.geegstar.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class GeegStarBookingService implements BookingService {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @Override
    public BookingResponse bookTalent(BookingRequest bookCreativeTalentRequest)
            throws UserNotFoundException {
        EventDetailRequest eventDetailsRequest = bookCreativeTalentRequest.getEventDetailRequest();
        Address eventAddress = modelMapper.map(eventDetailsRequest.getEventAddress(), Address.class);
        EventDetail eventDetail = modelMapper.map(eventDetailsRequest, EventDetail.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy, MM, dd, HH, mm");
        eventDetail.setEventDateAndTime(LocalDateTime.parse(eventDetailsRequest.getEventDateAndTime(), formatter));
        eventDetail.setEventAddress(eventAddress);
        User talent = userService.findUserById(bookCreativeTalentRequest.getTalentId());
        User planner = userService.findUserById(bookCreativeTalentRequest.getPlannerId());
        Calendar calendar = new Calendar();
        calendar.setTalent(talent);
        calendar.setEventDateAndTime(LocalDateTime.parse(eventDetailsRequest.getEventDateAndTime(), formatter));
        Booking booking = new Booking();
        booking.setTalent(talent);
        booking.setPlanner(planner);
        booking.setEventDetail(eventDetail);
        booking.setCalendar(calendar);
        Booking savedBooking = bookingRepository.save(booking);
        return modelMapper.map(savedBooking, BookingResponse.class);
    }

    @Override
    public AcceptBookingResponse acceptBooking (AcceptBookingRequest acceptBookingRequest)
            throws BookingNotFoundException, UserNotFoundException {
        Long bookingId = acceptBookingRequest.getBookingId();
        Booking foundBooking = findBookingById(bookingId);
        Calendar calendar = foundBooking.getCalendar();
        Long bookedTalentId = foundBooking.getTalent().getId();
        Long givenTalentIdFromRequest = acceptBookingRequest.getTalentId();
        if (Objects.equals(bookedTalentId, givenTalentIdFromRequest)) {
            foundBooking.setAccepted(true);
            calendar.setIsBooked(true);
            bookingRepository.save(foundBooking);
            return new AcceptBookingResponse("Successful");
        }
        return new AcceptBookingResponse("Un-Successful");
    }

    public Booking findBookingById(Long bookingId) throws BookingNotFoundException {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() ->
                        new BookingNotFoundException(String.format("The booking with %d id is not found", bookingId)));
    }

    @Override
    public List<UserBookingResponse> getUserBookings(GetUserBookingsRequest getUserBookingsRequest) throws UserNotFoundException {
        User user = userService.findUserById(getUserBookingsRequest.getUserId());
        Pageable pageable = PageRequest.of(getUserBookingsRequest.getPageNumber() - 1, getUserBookingsRequest.getPageSize());
        boolean isA_PlannerRequest = Role.PLANNER.equals(getUserBookingsRequest.getUserRole());
        Page<Booking> bookingPage = null;
        if (isA_PlannerRequest) bookingPage = bookingRepository.findAllByPlanner(user, pageable);
        if(!isA_PlannerRequest) bookingPage = bookingRepository.findAllByTalent(user, pageable);
        List<Booking> bookings = bookingPage.getContent();
        return bookings.stream()
                .filter(booking -> {
                    Long plannerIdOfBooking = booking.getPlanner().getId();
                    Long talentIdOfBooking = booking.getTalent().getId();
                    Long userId = getUserBookingsRequest.getUserId();
                    boolean isUserBooking = false;
                    if (isA_PlannerRequest) isUserBooking = Objects.equals(plannerIdOfBooking, userId);
                    if(!isA_PlannerRequest) isUserBooking = Objects.equals(talentIdOfBooking, userId);
                    return isUserBooking;
                })
                .map(booking -> {
                    User planner = booking.getPlanner();
                    User talent = booking.getTalent();
                    EventDetail eventDetail = booking.getEventDetail();
                    Address address = eventDetail.getEventAddress();
                    Calendar calendar = booking.getCalendar();
                    boolean isAccepted = booking.isAccepted();
                    Long bookingId = booking.getId();
                    return getUserBookingResponse(
                            isA_PlannerRequest,
                            talent, planner, 
                            calendar, address,
                            eventDetail, bookingId, 
                            isAccepted);
                })
                .toList();
    }

    private UserBookingResponse getUserBookingResponse(boolean isA_PlannerRequest, User talent, User planner, Calendar calendar, Address address, EventDetail eventDetail, Long bookingId, boolean isAccepted) {
        UserBookingResponse userBookingResponse = new UserBookingResponse();
        if (isA_PlannerRequest) setBookingResponseTalentResponse(talent, userBookingResponse);
        if(!isA_PlannerRequest) setBookingResponsePlannerResponse(planner, userBookingResponse);
        BookingResponseVariables bookingResponseVariables = getMappedBookingResponseVariables(calendar, address, eventDetail);
        userBookingResponse.setId(bookingId);
        userBookingResponse.setEventDetail(bookingResponseVariables.bookingResponseEventDetailResponse());
        userBookingResponse.setCalendar(bookingResponseVariables.bookingResponseCalenderResponse());
        userBookingResponse.setAccepted(isAccepted);
        return userBookingResponse;
    }

    private BookingResponseVariables getMappedBookingResponseVariables(Calendar calendar, Address address, EventDetail eventDetail) {
        BookingResponseCalenderResponse bookingResponseCalenderResponse =
                modelMapper.map(calendar, BookingResponseCalenderResponse.class);
        BookingResponseAddressResponse bookingResponseAddressResponse =
                modelMapper.map(address, BookingResponseAddressResponse.class);
        BookingResponseEventDetailResponse bookingResponseEventDetailResponse =
                modelMapper.map(eventDetail, BookingResponseEventDetailResponse.class);
        bookingResponseEventDetailResponse.setEventAddress(bookingResponseAddressResponse);
        return new BookingResponseVariables(bookingResponseCalenderResponse, bookingResponseEventDetailResponse);
    }

    private record BookingResponseVariables(BookingResponseCalenderResponse bookingResponseCalenderResponse, BookingResponseEventDetailResponse bookingResponseEventDetailResponse) {
    }

    private void setBookingResponsePlannerResponse(User planner, UserBookingResponse userBookingResponse) {
        BookingResponsePlannerResponse bookingResponsePlannerResponse =
                modelMapper.map(planner, BookingResponsePlannerResponse.class);
        userBookingResponse.setPlannerResponse(bookingResponsePlannerResponse);
    }

    private void setBookingResponseTalentResponse(User talent, UserBookingResponse userBookingResponse) {
        BookingResponseTalentResponse bookingResponseTalentResponse =
                modelMapper.map(talent, BookingResponseTalentResponse.class);
        userBookingResponse.setTalentResponse(bookingResponseTalentResponse);
    }

    @Override
    public DeclineBookingResponse declineBooking(Long bookingId) throws BookingNotFoundException {
        Booking foundBooking = findBookingById(bookingId);
        foundBooking.setAccepted(false);
        bookingRepository.save(foundBooking);
        return new DeclineBookingResponse("Successful");
    }

}

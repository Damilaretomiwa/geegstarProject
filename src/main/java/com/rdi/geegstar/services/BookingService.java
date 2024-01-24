package com.rdi.geegstar.services;

import com.rdi.geegstar.data.models.Booking;
import com.rdi.geegstar.dto.requests.AcceptBookingRequest;
import com.rdi.geegstar.dto.requests.BookingRequest;
import com.rdi.geegstar.dto.requests.GetUserBookingsRequest;
import com.rdi.geegstar.dto.response.*;
import com.rdi.geegstar.exceptions.BookingNotFoundException;
import com.rdi.geegstar.exceptions.UserNotFoundException;

import java.util.List;

public interface BookingService {

    BookingResponse bookTalent(BookingRequest bookCreativeTalentRequest) throws UserNotFoundException;

    AcceptBookingResponse acceptBooking(AcceptBookingRequest acceptBookingRequest) throws BookingNotFoundException, UserNotFoundException;

    DeclineBookingResponse declineBooking(Long bookingId) throws BookingNotFoundException;

    Booking findBookingById(Long bookingId) throws BookingNotFoundException;

    List<UserBookingResponse> getUserBookings(GetUserBookingsRequest getUserBookingsRequest) throws UserNotFoundException;


}
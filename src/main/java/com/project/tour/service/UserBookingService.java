package com.project.tour.service;

import com.project.tour.domain.EstimateInquiry;
import com.project.tour.domain.EstimateInquiryForm;
import com.project.tour.domain.UserBooking;
import com.project.tour.domain.UserBookingForm;
import com.project.tour.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserBookingService {

    private final BookingRepository bookingRepository;

    //예약하기
    public void create(UserBookingForm userBookingForm){

        UserBooking userBooking = new UserBooking();

        userBooking.setDeparture(userBookingForm.getDeparture());
        userBooking.setTravelPeriod(userBookingForm.getTravelPeriod());
        userBooking.setACount(userBookingForm.getACount());
        userBooking.setBCount(userBookingForm.getBCount());
        userBooking.setCCount(userBookingForm.getCCount());
        userBooking.setRequest(userBookingForm.getRequest());
        userBooking.setBookingDate(LocalDateTime.now());
        userBooking.setBookingTotalPrice(userBookingForm.getBookingTotalPrice());
        userBooking.setBookingStatus(userBookingForm.getBookingStatus());
        userBooking.setBookingTotalCount(userBookingForm.getBookingTotalCount());

        bookingRepository.save(userBooking);
    }
}

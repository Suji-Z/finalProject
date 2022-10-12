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

        userBooking.setDepartureDate(userBookingForm.getDepartureDate());
        userBooking.setTravelPeriod(userBookingForm.getTravelPeriod());
        userBooking.setA_travelerCount(userBookingForm.getA_travelerCount());
        userBooking.setB_travelerCount(userBookingForm.getB_travelerCount());
        userBooking.setC_travelerCount(userBookingForm.getC_travelerCount());
        userBooking.setRequest(userBookingForm.getRequest());
        userBooking.setBookingDate(LocalDateTime.now());
        userBooking.setBookingTotalPrice(userBookingForm.getBookingTotalPrice());
        userBooking.setBookingStatus(userBookingForm.getBookingStatus());
        userBooking.setBookingTotalCount(userBookingForm.getBookingTotalCount());

        bookingRepository.save(userBooking);
    }
}

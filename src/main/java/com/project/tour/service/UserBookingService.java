package com.project.tour.service;

import com.project.tour.domain.UserBooking;
import com.project.tour.domain.UserBookingForm;
import com.project.tour.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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

    public Page<UserBooking> getBookingList(Pageable pageable) {

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable= PageRequest.of(
                pageable.getPageNumber()<=0?0:
                        pageable.getPageNumber()-1,
                pageable.getPageSize(),Sort.by(sorts));

        return bookingRepository.findAll(pageable);
    }
    }



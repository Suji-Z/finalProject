package com.project.tour.service;

import com.project.tour.domain.Member;
import com.project.tour.domain.Package;
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


    //예약 데이터 저장하기
    public void create(UserBookingForm userBookingForm, int bookingTotalPrice,
                       Package apackage, Member member){

        UserBooking userBooking = new UserBooking();

        userBooking.setDeparture(userBookingForm.getDeparture());
        userBooking.setArrival(userBookingForm.getArrival());
        userBooking.setTravelPeriod(userBookingForm.getTravelPeriod());
        userBooking.setACount(userBookingForm.getACount()); //코딩전
        userBooking.setBCount(userBookingForm.getBCount()); //코딩전
        userBooking.setCCount(userBookingForm.getCCount()); //코딩전
        userBooking.setRequest(userBookingForm.getRequest());
        userBooking.setBookingDate(LocalDateTime.now());
        userBooking.setBookingTotalPrice(bookingTotalPrice);
        userBooking.setBookingStatus(0);

        //성인, 아동, 유아 인원수 합산
        userBooking.setBookingTotalCount(userBooking.getACount()+ userBooking.getBCount()+ userBooking.getCCount());
        userBooking.setAPackage(apackage);
        userBooking.setMember(member);

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



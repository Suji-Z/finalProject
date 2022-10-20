package com.project.tour.service;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
        userBooking.setACount(userBookingForm.getACount());
        userBooking.setBCount(userBookingForm.getBCount());
        userBooking.setCCount(userBookingForm.getCCount());
        userBooking.setTravelerName(userBookingForm.getTravelerName());
        userBooking.setTravelerTel(userBookingForm.getTravelerTel());
        userBooking.setTravelerBirth(userBookingForm.getTravelerBirth());
        userBooking.setRequest(userBookingForm.getRequest());
        userBooking.setBookingDate(LocalDateTime.now());
        userBooking.setBookingTotalPrice(bookingTotalPrice);
        userBooking.setBookingStatus(0);
        userBooking.setBookingTotalCount(userBooking.getACount()+
                userBooking.getBCount()+ userBooking.getCCount()); //성인, 아동, 유아 인원수 합산
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

    //id 값으로 예약정보 조회
    public UserBooking getUserBooking(long bookingNum){

        Optional<UserBooking> result = bookingRepository.findById(bookingNum);

        return result.get();
    }

    //결제완료시 예약상태 변경
    public void modifyBookingStatus(UserBooking userBooking, int status){

        userBooking.setBookingStatus(status);
        bookingRepository.save(userBooking);
    }

    //방금 예약한 booking데이터 끌고 오기
    public UserBooking getRecentBooking(){

        long id = bookingRepository.maxPayNum();
        Optional<UserBooking> result = bookingRepository.findById(id);

        return result.get();
    }
}



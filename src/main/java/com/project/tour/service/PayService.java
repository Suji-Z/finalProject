package com.project.tour.service;

import com.project.tour.domain.*;
import com.project.tour.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PayRepository payRepository;

    //pay테이블 저장하기
    public void create(UserBooking userBooking, Member member, PayForm payForm){

        Pay pay = new Pay();

        pay.setUserBooking(userBooking);
        pay.setMember(member);
        pay.setPayDate(LocalDateTime.now());
        pay.setPayInfo(payForm.getPayInfo());
        pay.setPayTotalPrice(payForm.getTotalPrice());
        pay.setPayMethod(payForm.getPayMethod());

        payRepository.save(pay);
    }

    //
//    public long getPayNum(Member member, LocalDateTime payDate){
//
//        long payNum = payRepository.findByMemberAndPayDate(member, payDate).get().getId();
//
//        return payNum;
//    }

    //member가 예약한 정보중에 젤 최근에 예약한 정보 가져오기
    public  Pay getRecentPay(){

        long id = payRepository.maxPayNum();
        Optional<Pay> result = payRepository.findById(id);

        return result.get();
    }

    public Pay getPay(long payNum){

        Optional<Pay> result = payRepository.findById(payNum);

        return result.get();
    }
}

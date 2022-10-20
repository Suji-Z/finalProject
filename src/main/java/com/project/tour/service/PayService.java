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
    public void create(UserBooking userBooking, Member member, String payDate, PayForm payForm){

        Pay pay = new Pay();

        pay.setUserBooking(userBooking);
        pay.setMember(member);
        pay.setPayDate(payDate);
        pay.setPayInfo(payForm.getPayInfo());
        pay.setPayTotalPrice(payForm.getTotalPrice());
        pay.setPayMethod(payForm.getPayMethod());

        payRepository.save(pay);
    }

    //
    public long getPayNum(Member member, String payDate){

        long payNum = payRepository.findByMemberAndPayDate(member, payDate).get().getId();

        return payNum;
    }

    public Pay getPay(long payNum){

        Optional<Pay> result = payRepository.findById(payNum);

        return result.get();
    }
}

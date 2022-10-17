package com.project.tour.service;

import com.project.tour.domain.*;
import com.project.tour.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PayRepository payRepository;

    public void create(UserBooking userBooking, Member member, PayForm payForm){

        Pay pay = new Pay();

        pay.setUserBooking(userBooking);
        pay.setMember(member);
        pay.setPayDate(LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:SS")));
        pay.setPayMethod(payForm.getPayMethod());
        pay.setPayInfo(payForm.getPayInfo());

        payRepository.save(pay);

    }
}

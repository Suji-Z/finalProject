package com.project.tour.service;

import com.project.tour.domain.Pay;
import com.project.tour.domain.UserBooking;
import com.project.tour.repository.PayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminSalesService {

    private final PayRepository payRepository;

    public Page<Pay> getPayList(Pageable pageable) {



        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable= PageRequest.of(
                pageable.getPageNumber()<=0?0:
                        pageable.getPageNumber()-1,
                pageable.getPageSize(),Sort.by(sorts));

        return payRepository.findAll(pageable);
    }

    public Boolean paidInfo(Long id){

        return payRepository.existsByMember_id(id);
    }

}

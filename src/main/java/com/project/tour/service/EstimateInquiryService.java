package com.project.tour.service;

import com.project.tour.domain.EstimateInquiry;
import com.project.tour.domain.EstimateInquiryForm;
import com.project.tour.repository.EstimateRepository;
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

@RequiredArgsConstructor
@Service
public class EstimateInquiryService {

    private final EstimateRepository estimateRepository;

    //페이징처리
    public Page<EstimateInquiry> getList(Pageable pageable){

        List<Sort.Order> sort = new ArrayList<Sort.Order>();
        sort.add(Sort.Order.desc("id")); //EstimateNum

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ?
                        0 : pageable.getPageNumber() -1, //반환할 페이지
                pageable.getPageSize(), //반환할 리스트 갯수
                Sort.by(sort)); //정렬 매개변수 적용

        return estimateRepository.findAll(pageable);
    }

    //문의 올리기
    public void create(EstimateInquiryForm estimateInquiryForm){

        EstimateInquiry inquiry = new EstimateInquiry();

        inquiry.setTitle(estimateInquiryForm.getTitle());
        inquiry.setLocation(estimateInquiryForm.getLocation());
        inquiry.setA_count(estimateInquiryForm.getACount());
        inquiry.setB_count(estimateInquiryForm.getBCount());
        inquiry.setC_count(estimateInquiryForm.getCCount());
        inquiry.setStartDay(estimateInquiryForm.getStartDay());
        inquiry.setEndDay(estimateInquiryForm.getEndDay());
        inquiry.setPrice(Integer.parseInt(estimateInquiryForm.getPrice()));
        inquiry.setFlexibleDay(estimateInquiryForm.getFlexibleDay());
        inquiry.setContent(estimateInquiryForm.getContent());
        inquiry.setCreated(LocalDateTime.now());
        //inquiry.setEmail( );

        estimateRepository.save(inquiry);
    }
    //문의 수정하기
    
    //문의 삭제하기
}

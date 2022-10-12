package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        inquiry.setLocation1(estimateInquiryForm.getLocation1());
        inquiry.setLocation2(estimateInquiryForm.getLocation2());
        inquiry.setACount(estimateInquiryForm.getACount());
        inquiry.setBCount(estimateInquiryForm.getBCount());
        inquiry.setCCount(estimateInquiryForm.getCCount());
        inquiry.setStartDay(estimateInquiryForm.getStartDay());
        inquiry.setEndDay(estimateInquiryForm.getEndDay());
        inquiry.setPrice(estimateInquiryForm.getPrice());
        inquiry.setFlexibleDay(estimateInquiryForm.getFlexibleDay());
        inquiry.setContent(estimateInquiryForm.getContent());
        inquiry.setCreated(LocalDateTime.now());
        //inquiry.setEmail( );

        estimateRepository.save(inquiry);
    }
    //문의 수정하기
    public void modify(EstimateInquiry inquiry,EstimateInquiryForm inquiryForm){

        inquiry.setTitle(inquiryForm.getTitle());
        inquiry.setLocation1(inquiryForm.getLocation1());
        inquiry.setLocation2(inquiryForm.getLocation2());
        inquiry.setACount(inquiryForm.getACount());
        inquiry.setBCount(inquiryForm.getBCount());
        inquiry.setCCount(inquiryForm.getCCount());
        inquiry.setStartDay(inquiryForm.getStartDay());
        inquiry.setEndDay(inquiryForm.getEndDay());
        inquiry.setPrice(inquiryForm.getPrice());
        inquiry.setFlexibleDay(inquiryForm.getFlexibleDay());
        inquiry.setContent(inquiryForm.getContent());
        inquiry.setCreated(LocalDateTime.now());
        //inquiry.setEmail( );

        estimateRepository.save(inquiry);
    }
    //문의 삭제하기
    public void delete(EstimateInquiry inquiry){
        estimateRepository.delete(inquiry);
    }

    //id를 통한 문의 검색
    public EstimateInquiry getArticle(Long id) {

        Optional<EstimateInquiry> inquiry = estimateRepository.findById(id);

        if(inquiry.isPresent())
            return inquiry.get();
        else
            throw new DataNotFoundException("확인할수 없는 게시물 입니다.");
    }
}

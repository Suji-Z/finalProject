package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.EstimateInquiry;
import com.project.tour.domain.EstimateReply;
import com.project.tour.domain.EstimateReplyForm;
import com.project.tour.domain.Package;
import com.project.tour.repository.EstimateReplyRepository;
import com.project.tour.repository.EstimateRepository;
import com.project.tour.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EstimateReplyService {

    @Autowired
    private final EstimateReplyRepository estimateReplyRepository;
    @Autowired
    private final PackageRepository packageRepository;

    //답변하기
    public void create(EstimateInquiry inquiry,EstimateReplyForm replyForm){

        EstimateReply reply = new EstimateReply();

        reply.setTitle(replyForm.getTitle());
        reply.setContent(replyForm.getContent());
//        reply.setRecomPackage1(replyForm.getRecomPackage1());
//        reply.setRecomPackage2(replyForm.getRecomPackage2());
//        reply.setRecomPackage3(replyForm.getRecomPackage3());
        reply.setCreated(LocalDateTime.now());
        reply.setEstimateInquiry(inquiry);

        estimateReplyRepository.save(reply);
    }

    //답변수정하기
    public void modify(EstimateReply reply,EstimateReplyForm replyForm){

        reply.setTitle(replyForm.getTitle());
//        reply.setRecomPackage1(replyForm.getRecomPackage1());
//        reply.setRecomPackage2(replyForm.getRecomPackage2());
//        reply.setRecomPackage3(replyForm.getRecomPackage3());
        reply.setContent(replyForm.getContent());
        reply.setCreated(LocalDateTime.now());

        estimateReplyRepository.save(reply);
    }


    //답변삭제하기
    public void delete(EstimateReply reply){
        estimateReplyRepository.delete(reply);
    }

    //id를 통한 답변 검색
    public EstimateReply getArticle(Long id) {

        Optional<EstimateReply> reply = estimateReplyRepository.findById(id);

        if(reply.isPresent())
            return reply.get();
        else
            throw new DataNotFoundException("확인할수 없는 게시물 입니다.");
    }

    public List<Package> getPackages(EstimateInquiry inquiry){

        //1. 지역이 같을때
        String location2 = inquiry.getLocation2();

        //2. 출발일이 같을때
        String startDay = inquiry.getStartDay().replace("-","");
        System.out.println(startDay);
        String startDay1,startDay2;
//        if (inquiry.getFlexibleDay()) {
//            startDay1 = startDay - 7;
//            startDay2 = startDay + 7;
//        } else {
//            startDay1 = Integer.parseInt(startDay);
//            startDay2 = Integer.parseInt(startDay);
//        }
        //3. 가격이 같거나 작을때
        String price = inquiry.getPrice();

        //packageRepository.findByLocation2AndDepartureDateBetween(location2,startDay1,startDay2);

        List<Package> aPackage = packageRepository.findByLocation2(location2);

        return aPackage;
    }
}

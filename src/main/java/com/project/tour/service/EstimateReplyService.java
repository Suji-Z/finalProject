package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.EstimateInquiry;
import com.project.tour.domain.EstimateInquiryForm;
import com.project.tour.domain.EstimateReply;
import com.project.tour.domain.EstimateReplyForm;
import com.project.tour.repository.EstimateReplyRepository;
import com.project.tour.repository.EstimateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EstimateReplyService {

    private final EstimateReplyRepository estimateReplyRepository;
    private final EstimateRepository estimateRepository;

    //답변하기
    public void create(EstimateInquiry inquiry,EstimateReplyForm replyForm){

        EstimateReply reply = new EstimateReply();

        reply.setSubject(replyForm.getSubject());
        reply.setContent(replyForm.getContent());
        reply.setRecomPackage1(replyForm.getRecomPackage1());
        reply.setRecomPackage2(replyForm.getRecomPackage2());
        reply.setRecomPackage3(replyForm.getRecomPackage3());
        reply.setCreated(LocalDateTime.now());
        reply.setEstimateInquiry(inquiry);

        estimateReplyRepository.save(reply);
    }

    //답변수정하기
    public void modify(EstimateReply reply,EstimateReplyForm replyForm){

        reply.setSubject(replyForm.getSubject());
        reply.setRecomPackage1(replyForm.getRecomPackage1());
        reply.setRecomPackage2(replyForm.getRecomPackage2());
        reply.setRecomPackage3(replyForm.getRecomPackage3());
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
}

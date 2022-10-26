package com.project.tour.service;

import com.project.tour.domain.Member;
import com.project.tour.domain.ShortReview;
import com.project.tour.domain.ShortReviewReply;
import com.project.tour.repository.ShortReviewReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ShortReviewReplyService {

    private final ShortReviewReplyRepository shortReviewReplyRepository;


    public ShortReviewReply create(ShortReview shortReview, String content, Member member){

        ShortReviewReply shortReviewReply = new ShortReviewReply();

        shortReviewReply.setContent(content);
        shortReviewReply.setCreated(LocalDateTime.now());
        shortReviewReply.setShortReviewNum(shortReview);
        shortReviewReply.setUserName(member);

        shortReviewReplyRepository.save(shortReviewReply);

        return shortReviewReply;

    }




}

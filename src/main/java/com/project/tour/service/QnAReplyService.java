package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.*;
import com.project.tour.repository.QnAReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnAReplyService {

    private final QnAReplyRepository qnAReplyRepository;


    public QnA_Reply getReply (Long id) {

        Optional<QnA_Reply> reply = qnAReplyRepository.findById(id);

        if(reply.isPresent()){
            return reply.get();
        } else {
            throw new DataNotFoundException("답변 대기 중");
        }
    }

    //답글 달기
    public QnA_Reply write(QnA qnA, QnAReplyForm qnAReplyForm) {

        QnA_Reply qnA_reply = new QnA_Reply();

        qnA_reply.setTitle(qnAReplyForm.getTitle());
        qnA_reply.setReplycreated(LocalDateTime.now());
        qnA_reply.setContent(qnAReplyForm.getContent());
        qnA_reply.setQnaNum(qnA);


        return qnAReplyRepository.save(qnA_reply);
    }

    //답글 수정하기

    public void modify (QnA_Reply reply, QnAReplyForm replyForm){
        reply.setTitle(replyForm.getTitle());
        reply.setContent(replyForm.getContent());

        qnAReplyRepository.save(reply);

    }


    //답글 삭제하기
    public void delete(QnA_Reply reply) {

        qnAReplyRepository.delete(reply);
    }




}

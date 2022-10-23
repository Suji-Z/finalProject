package com.project.tour.service;


import com.project.tour.domain.Member;
import com.project.tour.domain.Notice;
import com.project.tour.domain.NoticeReply;
import com.project.tour.domain.PackageDate;
import com.project.tour.repository.MemberRepository;
import com.project.tour.repository.NoticeReplyRepository;
import com.project.tour.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class NoticeReplyService {

    private final NoticeReplyRepository noticeReplyRepository;
    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    public void create(String content, Long memberNum, Long noticeNum){

        NoticeReply noticeReply = new NoticeReply();

        noticeReply.setMember(memberRepository.findById(memberNum).get());
        noticeReply.setCreated(LocalDateTime.now());
        noticeReply.setNotice(noticeRepository.findById(noticeNum).get());
        noticeReply.setContent(content);

        noticeReplyRepository.save(noticeReply);

    }

    //답변 리스트 뽑아내기
    public List<NoticeReply> getList(Long noticeNum){

        Optional<Notice> notice = noticeRepository.findById(noticeNum);

        return noticeReplyRepository.findAllByNotice(notice.get());
    }

    //댓글 수정
    public void update(Long replyNum, Long noticeNum, Long memberNum, String content){

        NoticeReply noticeReply = noticeReplyRepository.findById(replyNum).get();

        noticeReply.setContent(content);
        noticeReply.setCreated(LocalDateTime.now());

        noticeReplyRepository.save(noticeReply);

    }

    //댓글 삭제
    public void delete(Long replyNum){

        NoticeReply noticeReply = noticeReplyRepository.findById(replyNum).get();

        noticeReplyRepository.delete(noticeReply);
    }
}

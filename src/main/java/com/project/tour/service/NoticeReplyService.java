package com.project.tour.service;


import com.project.tour.domain.Member;
import com.project.tour.domain.Notice;
import com.project.tour.domain.NoticeReply;
import com.project.tour.domain.PackageDate;
import com.project.tour.repository.NoticeReplyRepository;
import com.project.tour.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeReplyService {

    private final NoticeReplyRepository noticeReplyRepository;
    private final NoticeRepository noticeRepository;

    public void create(HashMap<String,Object> replyForm){

        NoticeReply noticeReply = new NoticeReply();

        noticeReply.setMember((Member)replyForm.get("id"));
        noticeReply.setCreated(LocalDateTime.now());
        noticeReply.setNotice((Notice)replyForm.get("notice"));
        noticeReply.setContent((String)replyForm.get("content"));

        noticeReplyRepository.save(noticeReply);

    }

    //답변 리스트 뽑아내기
    public List<NoticeReply> getList(Notice notice){

       return noticeReplyRepository.findAllByNotice(notice);
    }
}

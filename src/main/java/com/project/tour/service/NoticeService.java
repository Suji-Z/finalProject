package com.project.tour.service;

import com.project.tour.domain.Notice;
import com.project.tour.domain.NoticeForm;
import com.project.tour.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    public void create(NoticeForm noticeForm){

        Notice notice = new Notice();

        notice.setSubject(noticeForm.getSubject());
        notice.setCategory(noticeForm.getCategory());
        notice.setCreated(LocalDateTime.now());
        notice.setNoticeImage(noticeForm.getNoticeImage());
        notice.setContent(noticeForm.getContent());
        notice.setHitCount(0);

        noticeRepository.save(notice);

    }

}

package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeForm {

    private String category;

    private String subject;

    private String content;

    private LocalDateTime created;

    private String noticeImage;

    private int hitCount;
}

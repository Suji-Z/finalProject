package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeNum")
    private Long id;

    private String category;

    private String subject;

    private String content;

    private LocalDateTime created;

    private String noticeImage;

    private int hitCount;

    @OneToMany(mappedBy = "notice",cascade = CascadeType.REMOVE)
    private List<NoticeReply> noticeReplyList;

}
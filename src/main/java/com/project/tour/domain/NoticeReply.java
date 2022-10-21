package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class NoticeReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeReplyNum")
    private Long id;

    private String name;

    private LocalDateTime created;

    private String content;

    @ManyToOne
    private Notice notice;



}

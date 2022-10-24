package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class QnA_Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replyNum")
    private Long id;

    private String title; //글제목

    private String content; //글내용

    private LocalDateTime replycreated;


    //원글의 답글
    @ManyToOne
    @JoinColumn(name="qnaNum")
    private QnA qnaNum;

    @ManyToOne
    private Member member;

}

package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class QnA_Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replyNum")
    private Long id;

    private Integer ordernum;

    private Integer layer;

    private Integer parent;

    private String title;

    private String content;

    //원글의 답글
    @ManyToOne
    private QnA qna;


}

package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class QnA {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qnaNum")
    private Long id;

    private String qnacategory;

    private String subject;

    private String content;

    private String password;

    private LocalDateTime created;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;


}
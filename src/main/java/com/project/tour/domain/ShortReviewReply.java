package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
public class ShortReviewReply {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shorReviewReplyNum")
    private Long id;

    @Column(length = 3000)
    private String content;

    private LocalDateTime created;

    @ManyToOne
    private ShortReview shortReviewNum;

    @ManyToOne
    private Member userName;

}

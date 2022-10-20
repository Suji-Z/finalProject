package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Entity
public class Review_reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "replyNum")
    private Long id ;

    @Column (length = 3000)
    private String content;

    private LocalDateTime created;

    //댓글에 해당하는 리뷰
    @ManyToOne
    private Review reviewNum;

    @ManyToOne
    private Member author;

    @ManyToMany
    Set<Member> voter;

    ////결제완료한 패키지 정보
    @ManyToOne
    @JoinColumn(name = "packageNum")
    private Package reviewPackages;
}
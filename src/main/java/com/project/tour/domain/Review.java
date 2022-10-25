package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewNum")
    private Long id;

    @Column (length = 200)
    private String Subject;

    @Column(length = 3000)
    private String content;

    @Column (length = 100)
    private String reviewImage;

    private LocalDateTime created;

    @Column
    private Double score;

    @ManyToOne
    private Member author;

    //결제완료한 패키지 정보
    @ManyToOne
    @JoinColumn(name = "packageNum")
    private Package reviewPackages;

    //하나의 리뷰에 많은 댓글
    @OneToMany(mappedBy = "reviewNum", cascade = CascadeType.REMOVE)
    private List<Review_reply> replyList;

//    @ManyToMany
//    Set<Member> voter;

    @Transient
    public String getPhotosImagePath() {
        if (reviewImage == null || id == null) return null;

        return "/review-photo/" + id + "/" + reviewImage;
    }

    //리뷰에 좋아요
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review", cascade = CascadeType.REMOVE)
    private List<ReviewLike> reviewLikeList = new ArrayList<>();

    private int hitCount;



}
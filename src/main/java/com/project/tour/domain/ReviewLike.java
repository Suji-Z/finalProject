package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Getter
@Setter
public class ReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewNum")
    private Review review;

}

package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class ShortReview {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shortReviewNum")
    private Long id;

    @Column(length = 3000)
    private String content;

    private Double score;

    private LocalDateTime created;

    @ManyToOne
    private Member userName;

    @ManyToOne
    @JoinColumn(name = "packageNum")
    private Package packages;



}

package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class EstimateReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EstimateReplyNum")
    private Long id;

    @Column(length = 300)
    private String subject;

    @Column(length = 3000)
    private String content;

    private LocalDateTime created;

    private Integer layer;

    private Integer parent;

    private String RecomPackage1;
    private String RecomPackage2;
    private String RecomPackage3;

    /** Foreign key 생성
     * 하나의 문의글에 여러개의 답변
     * */
    @ManyToOne
    private EstimateInquiry estimateInquiry;


}

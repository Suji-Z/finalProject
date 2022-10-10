package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
public class EstimateReplyForm {

    private Long EstimateReplyNum;

    @Column(length = 300)
    private String subject;

    @Column(length = 3000)
    private String content;

    private String RecomPackage1;
    private String RecomPackage2;
    private String RecomPackage3;

    private LocalDateTime created;

}

package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class EstimateReplyForm {

    private Long EstimateReplyNum;

    @Column(length = 300)
    private String subject;

    @Column(length = 3000)
    private String content;

    private List<Long> RecomPackage;

    private LocalDateTime created;

}

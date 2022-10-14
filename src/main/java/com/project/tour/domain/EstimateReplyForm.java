package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
public class EstimateReplyForm {

    private Long EstimateReplyNum;

    @NotBlank
    @Column(length = 300)
    private String title;

    @NotBlank
    @Column(length = 3000)
    private String content;

    private String recomPackage;

    private LocalDateTime created;

}

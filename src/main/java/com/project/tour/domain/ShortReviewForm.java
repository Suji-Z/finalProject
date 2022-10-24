package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ShortReviewForm {

    @NotEmpty(message = "리뷰를 입력해주세요.")
    @Size(max = 3000)
    private String content;

    private Double score;
}

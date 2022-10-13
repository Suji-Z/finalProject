package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ReviewForm {

    @NotEmpty(message = "제목을 입력해주세요.")
    @Size (max = 200)
    private String subject;

    @NotEmpty(message = "내용을 입력해주세요.")
    @Size(max = 3000)
    private String content;

    private String reviewImage;

    private Double score;


}
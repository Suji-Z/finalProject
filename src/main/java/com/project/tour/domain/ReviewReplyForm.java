package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ReviewReplyForm {

    @NotEmpty(message = "내용을 입력해주세요.")
    @Size(max = 3000)
    private String content;




}

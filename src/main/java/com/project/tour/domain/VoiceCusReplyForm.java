package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class VoiceCusReplyForm {

    @NotEmpty(message = "제목을 입력해주세요")
    private String subject;

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;
}

package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class VoiceCusForm {

    @NotEmpty(message = "제목을 입력해주세요")
    @Size(max = 100)
    private String subject;

    private String types;

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;
}

package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter @Setter
public class PwdUpdateForm {

    @NotEmpty(message = "필수 입력 항목 입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호 형식이 다릅니다. ")
    private String password1;

    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String password2;



}

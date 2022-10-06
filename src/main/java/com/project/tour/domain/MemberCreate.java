package com.project.tour.domain;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberCreate {

    @Email(message = "올바른 이메일 형식이 아닙니다.")
    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String email;

    @NotEmpty(message = "필수 입력 항목 입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$",
            message = "비밀번호는 영문 대소문자,숫자,특수문자를 1개 이상 포함한 8~16자리수로 입력해주세요. ")
    private String password1;

    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String password2;

    @Size(min = 3,max = 20)
    @NotEmpty(message = "필수 입력 항목 입니다.")
    private String name;

    private String birth;

    @NotEmpty(message = "필수 입력 항목 입니다.")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String phone_num;

    private String gender;

    @NotEmpty(message = "항목을 1개 이상 선택해주세요.")
    private String keyword1;

    private String keyword2;

    private String keyword3;

}

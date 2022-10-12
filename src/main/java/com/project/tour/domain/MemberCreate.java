package com.project.tour.domain;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter @Setter
public class MemberCreate {

    @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", message = "이메일 형식이 올바르지 않습니다.")
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

    private String sms_num;

    /*
        private String total_agree;

        @NotEmpty(message = "필수 항목 동의가 필요합니다.")
        private String agree1;

        @NotEmpty(message = "필수 항목 동의가 필요합니다.")
        private String agree2;

        @NotEmpty(message = "필수 항목 동의가 필요합니다.")
        private String agree3;
    */
    private String keyword;


}

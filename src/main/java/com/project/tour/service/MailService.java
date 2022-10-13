package com.project.tour.service;

import com.project.tour.domain.MailDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;

    private static final String title = "[여행어때] 임시 비밀번호 안내 이메일입니다.";
    private static final String message = "안녕하세요. 여행어때 임시 비밀번호 안내 메일입니다." + "\n" +
            "회원님의 임시 비밀번호는 아래와 같습니다. 로그인 후 반드시 비밀번호를 변경해주시길 바랍니다."+"\n";
    static final String fromAddress = "dorlti1215@gmail.com";

    public MailDTO createMail(String tmpPassword,String email){
        MailDTO mailDTO = MailDTO.builder()
                .address(email)
                .title(title)
                .message(message + "[" + tmpPassword + "]")
                .fromAddress(fromAddress)
                .build();

        return mailDTO;
    }

    public void sendMail(MailDTO mailDTO){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailDTO.getAddress());
        mailMessage.setSubject(mailDTO.getTitle());
        mailMessage.setText(mailDTO.getMessage());
        mailMessage.setFrom(mailDTO.getFromAddress());
        mailMessage.setReplyTo(mailDTO.getFromAddress());

        mailSender.send(mailMessage);


    }
}

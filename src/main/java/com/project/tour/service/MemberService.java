package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Member;
import com.project.tour.domain.MemberCreate;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public boolean existByEmail(String email){
        return memberRepository.existsByEmail(email);
    }


    public Member create(MemberCreate memberCreate){
        Member member = new Member();

        member.setEmail(memberCreate.getEmail());
        member.setPassword(passwordEncoder.encode(memberCreate.getPassword1()));
        member.setName(memberCreate.getName());
        member.setBirth(memberCreate.getBirth());
        member.setPhone_num(memberCreate.getPhone_num());
        member.setCoupon(0.1);
        member.setKeyword(memberCreate.getKeyword());



        memberRepository.save(member);

        return member;
    }

    public Member getMember(String email){
        Optional<Member> member = memberRepository.findByEmail(email);

        if(member.isPresent()) {
            return member.get();
        }else {
            throw new DataNotFoundException("해당하는 사용자가 없습니다.");
        }
    }

    public String PhoneNumberCheck(String phone_num) throws CoolsmsException {
        String api_key = "NCSFY6SF28I6RD5D";
        String api_secret = "V29MJNRUKPBO1H4HDEJKWUFEQ4DUWM0K";

        Message coolsms = new Message(api_key,api_secret);

        Random rand = new Random();
        String numStr = "";

        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr += ran;
        }

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phone_num);    // 수신전화번호
        params.put("from", "01024875587");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "[TEST] 인증번호는" + "["+numStr+"]" + "입니다."); // 문자 내용 입력

        coolsms.send(params);

        return numStr;

    }

}

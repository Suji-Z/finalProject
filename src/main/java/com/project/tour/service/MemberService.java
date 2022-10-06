package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Member;
import com.project.tour.domain.MemberCreate;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public boolean existByEmail(String email){
        return memberRepository.existsByEmail(email);
    }

    public Member create(MemberCreate memberCreate){
        Member member = new Member();

        member.setEmail(memberCreate.getEmail());
        member.setPassword(memberCreate.getPassword1());
        member.setName(memberCreate.getName());
        member.setBirth(memberCreate.getBirth());
        member.setPhone_num(memberCreate.getPhone_num());
        member.setGender(memberCreate.getGender());
        member.setCoupon(0.1);
        member.setKeyword1(memberCreate.getKeyword1());
        member.setKeyword2(memberCreate.getKeyword2());
        member.setKeyword3(memberCreate.getKeyword3());

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

}

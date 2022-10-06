package com.project.tour.service;

import com.project.tour.domain.Member;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberSecurityService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Member> searchMember = memberRepository.findByEmail(email);

        //해당하는 이메일이 없으면
        if(!searchMember.isPresent()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        Member member = searchMember.get();

        List<GrantedAuthority> authorities = new ArrayList<>();

        return new User(member.getEmail(), member.getPassword(),authorities);
    }
}

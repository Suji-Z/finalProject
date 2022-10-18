package com.project.tour.oauth.service;


import com.project.tour.domain.Member;
import com.project.tour.domain.MemberRole;
import com.project.tour.oauth.dto.BaseAuthUserRepository;
import com.project.tour.oauth.dto.OAuthAttributes;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.model.BaseAuthUser;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BaseCustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest,OAuth2User>{

    @Autowired
    private final BaseAuthUserRepository baseAuthUserRepository;

    @Autowired
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthUserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oauthUserService.loadUser(userRequest);

        //간편 로그인 진행하는 플랫폼(google,kakao,naver...)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        //OAuth2로그인 진행시 (primary)key가 되는 필드값
        //구글: sub , 네이버: response, 카카오: id

        //뭔소린지 이해 x.. 걍 oau 데이터 불러오는 프레임워크의 공식.. 소셜 다 동일함
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        System.out.println(userNameAttributeName); //sub or response or id 가 찍힘..

        //로그인을 통해 가져온 Oauth2User의 속성을 담아두는 of메소드
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());


        //응답 받은 속성(JSON)
        System.out.print(attributes.getAttributes());

        //응답받은 속성을 authUser 객체에 넣음
        BaseAuthUser authUser = saveOrUpdate(attributes);

        //세션의 사용자 정보 저장
        httpSession.setAttribute("user", new SessionUser(authUser));


        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(authUser.getRolekey())),
                attributes.getAttributes(),attributes.getNameAttributeKey());
    }

    //구글의 사용자 정보가 업데이트 되었을 때 사용할 메소드
    //사용자의 이름이나 프로필 사진이 변경되면 User Entity에도 반영됨
    private BaseAuthUser saveOrUpdate(OAuthAttributes attributes) {

        BaseAuthUser authUser = baseAuthUserRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return baseAuthUserRepository.save(authUser);
    }

}

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
public class BaseCustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private final BaseAuthUserRepository baseAuthUserRepository;

    @Autowired
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest,OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        //간편 로그인 플랫폼
        String registrationId = userRequest.getClientRegistration().getRegistrationId();


        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint()
                .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName,oAuth2User.getAttributes());

        BaseAuthUser authUser = save(attributes);

        httpSession.setAttribute("user",new SessionUser(authUser));


        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(authUser.getRolevalue())),
                attributes.getAttributes(),attributes.getNameAttributeKey());
    }

    private BaseAuthUser save(OAuthAttributes attributes){
        BaseAuthUser authUser = baseAuthUserRepository.findByEmail(attributes.getEmail()).get();

        return baseAuthUserRepository.save(authUser);
    }
}

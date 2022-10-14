package com.project.tour.oauth.dto;

import com.project.tour.domain.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,String name,String email) {

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;

    }

    //OAuth2User에서 반환하는 사용자 정보는 MAP이기 때문에
    //ofGoogle 메소드에서 변환 작업을 함								//sub
    public static OAuthAttributes of(String registrationId,String userNameAttributeName,
                                     Map<String, Object> attributes) {

        //네이버,카카오 등 넘어오는 데이터 소셜 구분 작업 필요 구간
        if(registrationId.equals("kakao")) {

            //id				//사용자 정보
            return ofKakao("id", attributes);
        }

        if(registrationId.equals("naver")) {
            //얘는 uNA에 response가 담겨서 옴. 그래서 그냥 id만 써줌
            return ofNaver("id", attributes);
        }
        return ofGoogle(userNameAttributeName,attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName,Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder().name((String)response.get("name"))
                .email((String)response.get("email"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }


    private static OAuthAttributes ofKakao(String userNameAttributeName,Map<String, Object> attributes) {

        //kakao_account에 email이 있음
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");

        //kakao_account에 profile이라는 JSON객체가 있음(nickname,image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        return OAuthAttributes.builder().name((String)kakaoProfile.get("nickname"))
                .email((String)kakaoAccount.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }



    private static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String, Object> attributes) {

        return OAuthAttributes.builder().name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .email(email)
                .build();
    }
}

package com.project.tour.oauth.dto;

import com.project.tour.domain.Member;
import com.project.tour.oauth.model.BaseAuthRole;
import com.project.tour.oauth.model.BaseAuthUser;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
@Getter
public class OAuthAttributes {

    //OAuth2로 반환하는 user정보를 저장
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,String name,String email,String picture) {

        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;

    }

    //OAuth2User에서 반환하는 사용자 정보는 MAP이기 때문에
    //ofGoogle 메소드에서 변환 작업을 함								//sub
    public static OAuthAttributes of(String registrationId,String userNameAttributeName,
                                     Map<String, Object> attributes) {

        //네이버,카카오 등 넘어오는 데이터 소셜 구분 작업 필요 구간
        if(registrationId.equals("kakao")) {

            //id				//사용자 정보
            return ofKakao(userNameAttributeName, attributes);
        }

        if(registrationId.equals("naver")) {
            //얘는 uNA에 response가 담겨서 옴. 그래서 그냥 id만 써줌
            return ofNaver("id", attributes);
        }

        //sub 			 //사용자 정보
        return ofGoogle(userNameAttributeName,attributes);
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName,Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>)attributes.get("response");

        return OAuthAttributes.builder().name((String)response.get("name"))
                .email((String)response.get("email"))
                .picture((String)response.get("profile_image"))
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
                .picture((String)kakaoProfile.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }



    private static OAuthAttributes ofGoogle(String userNameAttributeName,Map<String, Object> attributes) {

        return OAuthAttributes.builder().name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //필요한 정보를 받으면..게스트->유저로 변환..? 무슨말임.. 걍 일케 써?
    public BaseAuthUser toEntity() {

        return BaseAuthUser.builder().name(name)
                .email(email).picture(picture).role(BaseAuthRole.GUEST)
                .build();

    }
}
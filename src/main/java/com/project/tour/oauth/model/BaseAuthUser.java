package com.project.tour.oauth.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
//@Setter //무의미.. 아래에서 넘어오는 값 자동으로 받도록 생성자 설계
@NoArgsConstructor
@Entity
public class BaseAuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BaseAuthRole role;

    //고객 역할 지정 (setter)
    @Builder			//소셜에서 넘어오는 데이터 받아주는 곳
    public BaseAuthUser(String name,String email,String picture,BaseAuthRole role) {

        this.name=name;
        this.email=email;
        this.picture=picture;
        this.role=role;

    }
    //사용자가 프로필 사진 변경시 적용될 부분..
    public BaseAuthUser update(String name,String picture) {
        this.name=name;
        this.picture=picture;

        return this;
    }

    public String getRolekey() {

        return this.role.getKey();
    }

}

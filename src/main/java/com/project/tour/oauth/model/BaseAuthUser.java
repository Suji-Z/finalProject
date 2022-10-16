package com.project.tour.oauth.model;

import com.project.tour.domain.MemberRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
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
    private MemberRole role;

    //고객 역할 지정 (setter)
    @Builder            //소셜에서 넘어오는 데이터 받아주는 곳
    public BaseAuthUser(String name, String email, MemberRole role) {

        this.name=name;
        this.email=email;
        this.picture=picture;
        this.role=role;

    }

    public BaseAuthUser update(String name,String picture) {
        this.name=name;
        this.picture=picture;

        return this;
    }


    public String getRolevalue() {

        return this.role.getValue();
    }

}

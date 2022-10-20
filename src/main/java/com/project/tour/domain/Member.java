package com.project.tour.domain;

import com.project.tour.oauth.model.BaseAuthRole;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String birth;

    @Column(name = "phone_num")
    private String phone;

    @Column
    private int point;

    private String coupons;

    private LocalDateTime createdDate;

    @Column
    private String keyword;
    private String social;


    public void updatePassword(String password){
        this.password = password;
    }

    @Builder            //소셜에서 넘어오는 데이터 받아주는 곳
    public Member(String name, String email,String social) {

        this.name=name;
        this.email=email;
        this.social=social;

    }


}

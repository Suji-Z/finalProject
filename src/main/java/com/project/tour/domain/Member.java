package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
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
    @Column
    private double coupon;

    @Column
    private String keyword;


    public void updatePassword(String password){
        this.password = password;
    }



}

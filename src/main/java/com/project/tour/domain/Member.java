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

    @Column(unique = true)
    private String phone_num;

    @Column
    private String gender;

    @Column
    private int point;
    @Column
    private double coupon;

    @Column
    private String keyword1;
    @Column
    private String keyword2;
    @Column
    private String keyword3;


}

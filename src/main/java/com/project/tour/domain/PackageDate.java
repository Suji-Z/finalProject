package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class PackageDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String departure;

    private Integer aprice;
    private Integer bprice;
    private Integer cprice;

    private Integer discount;
    private Integer remaincount;


    /** Foreign key 생성
     * 하나의 패키지상품에 여러개의 출발일
     * */
    @ManyToOne
    @JoinColumn(name="packageNum")
    private Package packages;
}

package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class PackageDate {

    @Id
    private LocalDateTime departureDate;

    private Long packageNum;

    private Integer a_price;
    private Integer b_price;
    private Integer c_price;

    private Integer discount;
    
    /** Foreign key 생성
     * 하나의 패키지상품에 여러개의 출발일 
     * */
    @ManyToOne
    private Package packages;
}

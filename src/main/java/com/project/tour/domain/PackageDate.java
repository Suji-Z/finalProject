package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class PackageDate {

    private Long packageNum;

    private Integer a_price;
    private Integer b_price;
    private Integer c_price;

    private LocalDateTime departureDate;
    private Integer discount;
}

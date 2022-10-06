package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long packageNum;

    private String location;
    private String packageName;
    private String hotelName;
    private LocalDateTime postStart;
    private LocalDateTime postEnd;
    private Integer count;
    private Integer travelPeriod;
    private String previewImage;
    private String detailImage;
    private String packgeInfo;
    private Integer hitCount;
    private String keyword;
    private String transport;



}

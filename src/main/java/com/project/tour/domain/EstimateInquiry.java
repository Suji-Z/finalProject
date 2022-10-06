package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class EstimateInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long EstimateNum;

    private String email;

    @Column(length = 500)
    private String title;

    private String location;

    private Integer a_count;
    private Integer b_count;
    private Integer c_count;

    private LocalDateTime startDay;
    private LocalDateTime endDay;

    private Integer price;

    private Boolean flexibleDay;

    @Column(columnDefinition = "TEXT",length = 3000)
    private String content;


}

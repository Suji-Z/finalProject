package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

@Getter
@Setter
public class EstimateInquiryForm {

    private String title;

    private String location;

    private int aCount;

    @Nullable
    private int bCount;

    @Nullable
    private int cCount;

    private String startDay;

    private String endDay;

    private String price;

    private Boolean flexibleDay;

    private String content;

    private LocalDateTime created;

}

package com.project.tour.domain;

import lombok.Data;

@Data
public class EstimateSearchCondition {

    //날짜 //잔여좌석 //가격범위 //지역

    private String flxstartday1;
    private String flxstartday2;
    private String startday;
    private Integer remaincount;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer price;
    private String lcoation2;
    private Integer travelPeriod;

}

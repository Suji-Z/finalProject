package com.project.tour.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PackageSearchCondition {

    private String location2;
    private String startday;
    private Integer totcount;
    private String keyword;
    private String transport;
    private Integer travelPeriod;
    private Integer pricerangestr;
    private Integer pricerangeend;

}

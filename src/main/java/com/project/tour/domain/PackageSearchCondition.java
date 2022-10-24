package com.project.tour.domain;

import lombok.Data;

import java.util.List;

@Data
public class PackageSearchCondition {

    private String location1;
    private String location2;
    private String startday;
    private Integer pricerangestr;
    private Integer pricerangeend;
    private List<Integer> travelPeriod;
    private List<String> transport;
    private Integer totcount;
    private String keyword;




}

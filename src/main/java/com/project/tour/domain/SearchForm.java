package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchForm {

    private String location1;
    private String location2;
    private String departure;
    private Integer totcount;
    private String keyword;
    private Integer pricerangestr;
    private Integer pricerangeend;
    private List<String> transports;
    private List<String> travelPeriods;

}

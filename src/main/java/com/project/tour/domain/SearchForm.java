package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchForm {

    private String location;
    private String departure;
    private Integer totcount;
    private String keyword;
    private Integer pricerangestr;
    private Integer pricerangeend;
    private Double reviewstar;
    private String transport;
    private Integer travelPeriod;

}

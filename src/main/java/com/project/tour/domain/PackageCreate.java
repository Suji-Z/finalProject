package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
public class PackageCreate {

    private Long id;

    @Size(max = 20)
    private String location1;

    private String location2;

    @NotEmpty(message = "패키지 이름을 입력하세요")
    private String packageName;

    @ManyToOne
    private Integer a_price;

    @ManyToOne
    private Integer b_price;

    @ManyToOne
    private Integer c_price;

    private String HotelName;

    private String postStart;

    private String postEnd;

    private Integer count;

    @NotNull(message = "여행 기간을 입력하세요")
    private Integer travelPeriod;

    private String previewImage;

    private String detailImage;

    private String packageInfo;

    private String hitCount;

    @NotEmpty(message = "패키지 상품의 키워드를 입력하세요")
    private String keyword;



                                                                                                                                                                                                       private String transport;
}

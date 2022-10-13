package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class PackageCreate {

    @NotEmpty(message = "지역을 입력하세요")
    @Size(max = 20)
    private String location;

    @NotEmpty(message = "패키지 이름을 입력하세요")
    private String packageName;

    @ManyToOne
    private Integer a_price;

    @ManyToOne
    private Integer b_price;

    @ManyToOne
    private Integer c_price;

    @NotEmpty(message = "숙소 이름을 입력하세요")
    private String HotelName;

    @NotEmpty(message = "상품 게시일을 입력하세요")
    private String postStart;

    @NotEmpty(message = "상품 마감일을 입력하세요")
    private String postEnd;

    private Integer count;

    @NotEmpty(message = "여행 기간을 입력하세요")
    private Integer travelPeriod;

    private String previewImage;

    private String detailImage;

    private String packageInfo;

    private String hitCount;

    @NotEmpty(message = "패키지 상품의 키워드를 입력하세요")
    private String keyword;


                                                                                                                                                                                                       private String transport;
}

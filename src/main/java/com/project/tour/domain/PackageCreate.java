package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class PackageCreate {

    @NotEmpty(message = "여행지를 선택해주세요.")
    private String location1;

    @NotEmpty(message = "여행지를 선택해주세요")
    private String location2;

    @NotEmpty(message = "패키지 이름을 입력하세요.")
    private String packageName;

    @NotNull
    private Integer aprice;

    @NotNull
    private Integer bprice;

    @NotNull
    private Integer cprice;

    @NotEmpty(message = "숙소 이름을 입력하세요")
    private String hotelName;

    @NotEmpty(message = "상품 게시일을 입력해주세요.")
    private String postStart;

    @NotEmpty(message = "상품 게시 마감일을 입력해주세요.")
    private String postEnd;

    @NotNull
    private Integer count;

    private Integer discount;

    @NotNull
    private Integer travelPeriod;

    private String packageInfo;

    private String hitCount;

    @NotEmpty(message = "여행 키워드를 입력해주세요.")
    private String keyword;

    private Long packageNum;

    private String departure;



                                                                                                                                                                                                       private String transport;
}

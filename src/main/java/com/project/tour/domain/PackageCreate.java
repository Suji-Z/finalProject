package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class PackageCreate {

    @Size(max = 20)
    private String location1;

    private String location2;

    private String packageName;

    private Integer aprice;

    private Integer bprice;

    private Integer cprice;

    private String HotelName;

    private String postStart;

    private String postEnd;

    private Integer count;

    private Integer discount;

    private Integer travelPeriod;

    private String previewImage;

    private String detailImage;

    private String packageInfo;

    private String hitCount;

    private String keyword;

    private Long packageNum;



                                                                                                                                                                                                       private String transport;
}

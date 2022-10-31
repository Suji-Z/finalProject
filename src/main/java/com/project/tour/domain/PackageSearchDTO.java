package com.project.tour.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PackageSearchDTO {

    private Long id;
    private String packageName;
    private String previewImage;

    private String location1;
    private String location2;
    private String packageInfo;
    private String postStart;
    private String postEnd;
    private Integer travelPeriod;
    private String keyword;
    private Integer aprice;
    private Integer discount;
    private Integer count;

    private Integer hitCount;


    private Double reviewScore;

    private Long reviewCount;



    @QueryProjection
    public PackageSearchDTO(Long id, String packageName, String previewImage, String location1,String location2, String packageInfo, String postStart, String postEnd, Integer travelPeriod, String keyword, Integer aprice, Integer discount, Integer count, Integer hitCount,Double reviewScore,Long reviewCount) {
        this.id = id;
        this.packageName = packageName;
        this.previewImage = previewImage;
        this.location1 = location1;
        this.location2 = location2;
        this.packageInfo = packageInfo;
        this.postStart = postStart;
        this.postEnd = postEnd;
        this.travelPeriod = travelPeriod;
        this.keyword = keyword;
        this.aprice = aprice;
        this.discount = discount;
        this.count = count;
        this.hitCount = hitCount;
        this.reviewScore = reviewScore;
        this.reviewCount = reviewCount;

    }
}

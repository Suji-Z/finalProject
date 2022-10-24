package com.project.tour.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PackageSearchDTO {

    private Long id;
    private String packageName;
    private String previewImage;
    private String location2;
    private String packageInfo;
    private String postStart;
    private String postEnd;
    private Integer travelPeriod;
    private String keyword;
    private Integer aprice;
    private Integer discount;

    //추후 희진이 파트 쇼트리뷰 추가

    @QueryProjection
    public PackageSearchDTO(Long id, String packageName, String previewImage, String location2, String packageInfo, String postStart, String postEnd, Integer travelPeriod, String keyword, Integer aprice, Integer discount) {
        this.id = id;
        this.packageName = packageName;
        this.previewImage = previewImage;
        this.location2 = location2;
        this.packageInfo = packageInfo;
        this.postStart = postStart;
        this.postEnd = postEnd;
        this.travelPeriod = travelPeriod;
        this.keyword = keyword;
        this.aprice = aprice;
        this.discount = discount;
    }
}

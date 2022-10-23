package com.project.tour.domain;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class EstimateSearchDTO {

    private Long id;
    private String packageName;
    private String previewImage;
    private Integer aprice;

    @QueryProjection

    public EstimateSearchDTO(Long id, String packageName, String previewImage, Integer aprice) {
        this.id = id;
        this.packageName = packageName;
        this.previewImage = previewImage;
        this.aprice = aprice;
    }
}

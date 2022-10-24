package com.project.tour.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "packageNum")
    private Long id;

    private String location1;
    private String location2;

    @Column(length = 300)
    private String packageName;

    private String hotelName;

    private String postStart;
    private String postEnd;

    private Integer count;

    private Integer travelPeriod;

    private String previewImage;
    private String detailImage;

    @Column(length = 3000)
    private String packageInfo;

    private Integer hitCount;

    private String keyword;

    /**항공사 이름*/
    private String transport;

    /**하나의 패키지상품에 여러개의 출발일
     * 패키지 삭제시 각 출발일 상세 데이터 함께 삭제
     * */
    @OneToMany(mappedBy = "packages",cascade = CascadeType.REMOVE)
    private List<PackageDate> packagedatelist;

    //이미지 업로드
    @Transient
    public String getPhotosImagePath1() {
        if (previewImage == null || id == null) return null;

        return "/package-preview/" + id + "/" + previewImage;
    }

    public String getPhotosImagePath2() {
        if (detailImage == null || id == null) return null;

        return "/package-detail/" + id + "/" + detailImage;
    }

}

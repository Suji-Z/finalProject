package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class EstimateInquiryForm {

    @NotBlank(message = "제목을 입력해주세요")
    private String title;

    private String location;

    @NotNull(message = "성인은 반드시 1인 이상이어야합니다.")
    private Integer aCount;

    @Nullable
    private Integer bCount;

    @Nullable
    private Integer cCount;

    private String startDay;

    private String endDay;

    private String price;

    private Boolean flexibleDay;

    private String content;

    private LocalDateTime created;

}

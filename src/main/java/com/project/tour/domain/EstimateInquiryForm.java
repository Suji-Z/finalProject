package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class EstimateInquiryForm {

    @NotBlank
    private String title;

    private String location1;
    private String location2;

    @NotNull
    @Min(1)
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

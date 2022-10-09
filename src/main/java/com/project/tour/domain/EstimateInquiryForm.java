package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class EstimateInquiryForm {

    private String title;

    private String location;

    @NotNull
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

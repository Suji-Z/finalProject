package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PayForm {

    String payMethod;

    int totalPrice;

    LocalDateTime payDate;

    String payInfo;
}

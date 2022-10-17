package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PayForm {

    String payMethod;

    int totalPrice;

    String payDate;

    String payInfo;
}

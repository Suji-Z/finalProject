package com.project.tour.service;

import com.project.tour.domain.PackageDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PackageDateServiceTest {

    @Autowired
    PackageDateService packageDateService;

    @Test
    void getPackageDate() {

        long packageNum = 2;
        String departureDate = "20220101";
        PackageDate result = packageDateService.getPackageDate(packageNum, departureDate);
        System.out.println("result.getC_price() = " + result.getC_price());
    }
}
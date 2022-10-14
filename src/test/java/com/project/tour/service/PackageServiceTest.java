package com.project.tour.service;

import com.project.tour.domain.Package;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PackageServiceTest {

    @Autowired
    PackageService packageService;


    @Test
    void getPackage() {
        long packageNum=2;
        
        Package result = packageService.getPackage(packageNum);
        /*System.out.println(result.getLocation());*/
    }
}
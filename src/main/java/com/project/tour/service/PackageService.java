package com.project.tour.service;


import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Member;
import com.project.tour.domain.Package;
import com.project.tour.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PackageService {

    private PackageRepository packageRepository;

    //특정 packageNum으로 package data 출력(임시)
    public Package getPackage(long packageNum){
        Optional<Package> packageData = packageRepository.findById(packageNum);
        return packageData.get();
    }

}

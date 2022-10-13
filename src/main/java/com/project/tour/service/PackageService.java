package com.project.tour.service;


import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Member;
import com.project.tour.domain.Package;
import com.project.tour.domain.PackageDate;
import com.project.tour.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PackageService {

    private final PackageRepository packageRepository;

    //페이징처리
    public Page<Package> getList(Pageable pageable){

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable= PageRequest.of(
                pageable.getPageNumber()<=0?0:
                        pageable.getPageNumber()-1,
                pageable.getPageSize(),Sort.by(sorts));

        return packageRepository.findAll(pageable);
    }


    //데이터 불러오기 위한 임시
    private void create(Package apackage,PackageDate packageDate){
        Package packages = new Package();
        PackageDate packageDates = new PackageDate();

        packages.setId(apackage.getId());
        packages.setLocation(apackage.getLocation());
        packages.setPackageName(apackage.getPackageName());
        packages.setHotelName(apackage.getHotelName());
        packages.setPostStart(apackage.getPostStart());
        packages.setPostEnd(apackage.getPostEnd());
        packages.setCount(apackage.getCount());
        packages.setTravelPeriod(apackage.getTravelPeriod());
        packages.setPreviewImage(apackage.getPreviewImage());
        packages.setDetailImage(apackage.getDetailImage());
        packages.setPackageInfo(apackage.getPackageInfo());
        packages.setHitCount(apackage.getHitCount());
        packages.setKeyword(apackage.getKeyword());
        packages.setTransport(apackage.getTransport());
        packageDates.setDeparture(packageDate.getDeparture());
        packageDates.setDiscount(packageDate.getDiscount());
        packageDates.setA_price(packageDate.getA_price());
        packageDates.setB_price(packageDate.getB_price());
        packageDates.setC_price(packageDate.getC_price());
        packageDates.setRemainCount(packageDate.getRemainCount());

        packageRepository.save(packages);
    }




    //특정 packageNum으로 package data 출력(임시)
    public Package getPackage(long packageNum){
        Optional<Package> packageData = packageRepository.findById(packageNum);
        return packageData.get();


        


    }

}

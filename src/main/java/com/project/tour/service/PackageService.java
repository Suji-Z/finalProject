package com.project.tour.service;


import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Member;
import com.project.tour.domain.Package;
import com.project.tour.domain.PackageDate;
import com.project.tour.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Slf4j
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

    //지역별 페이징처리
    public Page<Package> getLocationList(String location,Pageable pageable){

        if(location.equals("seogwipo")){
            location="서귀포";
        }else if(location.equals("jungmun")){
            location="중문";
        }else if(location.equals("aewol")){
            location="애월";
        }else if(location.equals("jejusi")) {
            location = "제주시";
        }

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable= PageRequest.of(
                pageable.getPageNumber()<=0?0:
                        pageable.getPageNumber()-1,
                pageable.getPageSize(),Sort.by(sorts));

        return packageRepository.findByLocation2(location, pageable);
    }


    //데이터 불러오기 위한 임시
    private void create(Package apackage,PackageDate packageDate){
        Package packages = new Package();
        PackageDate packageDates = new PackageDate();

        packages.setId(apackage.getId());
        packages.setLocation1(apackage.getLocation1());
        packages.setLocation2(apackage.getLocation2());
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
        packageDates.setAprice(packageDate.getAprice());
        packageDates.setBprice(packageDate.getBprice());
        packageDates.setCprice(packageDate.getCprice());
        packageDates.setRemaincount(packageDate.getRemaincount());

        packageRepository.save(packages);
    }




    //특정 packageNum으로 package data 출력(임시)
    public Package getPackage(long packageNum){
        Optional<Package> packageData = packageRepository.findById(packageNum);
        return packageData.get();


        


    }

}

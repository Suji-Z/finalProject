package com.project.tour.service;


import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.repository.JejuPackageRepository;
import com.project.tour.repository.JejuSpecification;
import com.project.tour.repository.PackageRepository;
import com.project.tour.repository.PackageSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class PackageService {

    @Autowired
    private final PackageRepository packageRepository;

    @Autowired
    private final JejuPackageRepository jejuRepository;
    @Autowired
    private final PackageSearchRepository searchRepository;

    //페이징처리
    public Page<Package> getList(Pageable pageable) {

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 :
                        pageable.getPageNumber() - 1,
                pageable.getPageSize(), Sort.by(sorts));

        return packageRepository.findAll(pageable);
    }
    public List<Package> getSearch(String keyword) {

        Specification<Package> spec = Specification.where(JejuSpecification.equalKeyword(keyword));

        return jejuRepository.findAll(spec);
    }

    public List<Package> getKeyword(List<String> keyword){


        return packageRepository.findByKeywordIn(keyword);
    }

    public Page<PackageSearchDTO> getSearchList(String location2, String date, Integer count, String keyword,List<String> transport,
                                                List<Integer> travelPeriod, Integer pricerangestr, Integer pricerangeend ,Pageable pageable) {

        PackageSearchCondition condition = new PackageSearchCondition();

        condition.setLocation1("제주");
        condition.setLocation2(location2);
        condition.setStartday(date);
        condition.setTotcount(count);
        condition.setKeyword(keyword);
        condition.setTransport(transport);
        condition.setTravelPeriod(travelPeriod);
        condition.setPricerangestr(pricerangestr);
        condition.setPricerangeend(pricerangeend);

        //페이징처리
        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 :
                        pageable.getPageNumber() - 1,
                pageable.getPageSize(), Sort.by(Sort.Direction.DESC));

        return searchRepository.searchByWhere(condition,pageable);

    }

    //데이터 불러오기 위한 임시
    private void create(Package apackage, PackageDate packageDate) {
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
    public Package getPackage(long packageNum) {

        Optional<Package> packageData = packageRepository.findById(packageNum);

        return packageData.get();


    }

}

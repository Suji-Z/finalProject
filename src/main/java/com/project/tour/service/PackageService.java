package com.project.tour.service;


import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.domain.PackageDate;
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
import org.springframework.transaction.annotation.Transactional;

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


    //조회수 증가
    public void updateHitCount(int hitCount, Long id){

        Optional<Package> packages = packageRepository.findById(id);
        packages.get().setHitCount(hitCount);

        packageRepository.save(packages.get());

    }



    public List<Package> getHitList(){

        return packageRepository.findAllByOrderByBookingCntDesc();
    }

    public List<Package> getSearch(String keyword) {

        Specification<Package> spec = Specification.where(JejuSpecification.equalKeyword(keyword));

        return jejuRepository.findAll(spec);
    }

    public List<Package> getKeyword(List<String> keyword){


        return packageRepository.findByKeywordIn(keyword);
    }

    public Page<PackageSearchDTO> getSearchList(String location1,String location2, String date, Integer count, String keyword,List<String> transport,
                                                List<Integer> travelPeriod, Integer pricerangestr, Integer pricerangeend ,Pageable pageable) {


        PackageSearchCondition condition = new PackageSearchCondition();

        condition.setLocation1(location1);
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
                pageable.getPageSize(), Sort.by(sorts));

        return searchRepository.searchByWhere(condition,pageable);
    }

    public Page<PackageSearchDTO> getSearchListabroad(String location1,String location2, String date, Integer count, String keyword,List<String> transport,
                                                List<Integer> travelPeriod, Integer pricerangestr, Integer pricerangeend , Pageable pageable) {


        PackageSearchCondition condition = new PackageSearchCondition();



        condition.setLocation1(location1);
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
                pageable.getPageSize(), Sort.by(sorts));

        return searchRepository.searchByWhere(condition,pageable);

    }




    //특정 packageNum으로 package data 출력
    public Package getPackage(long packageNum) {

        Optional<Package> packageData = packageRepository.findById(packageNum);

        return packageData.get();


    }




}

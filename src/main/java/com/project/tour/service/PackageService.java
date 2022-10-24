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

//    @Transactional
//    public int updateHitCount(Long id){
//        return packageRepository.updateHitCount(id);
//    }


    @Autowired
    private final PackageRepository packageRepository;

    @Autowired
    private final JejuPackageRepository jejuRepository;
    @Autowired
    private final PackageSearchRepository searchRepository;

    //페이징처리(Ajax)
    /**
     * 게시글 리스트 조회 - (With. pagination information)
     */
//    public Map<String, Object> findAll(CommonParams params) {

        // 게시글 수 조회
//        int count = PackageRepository.count(params);

        // 등록된 게시글이 없는 경우, 로직 종료
//        if (count < 1) {
//            return Collections.emptyMap();
//        }

//        // 페이지네이션 정보 계산
//        Pagination pagination = new Pagination(params);
//        params.setPagination(pagination);
//
//        // 게시글 리스트 조회
//        List<Package> list = PackageRepository.findAll(params);
//
//        // 데이터 반환
//        Map<String, Object> response = new HashMap<>();
//        response.put("params", params);
//        response.put("list", list);
//        return response;
//    }




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

    public Page<PackageSearchDTO> getSearchListabroad(String location2, String date, Integer count, String keyword,List<String> transport,
                                                List<Integer> travelPeriod, Integer pricerangestr, Integer pricerangeend ,Pageable pageable) {


        PackageSearchCondition condition = new PackageSearchCondition();

        condition.setLocation1("유럽");
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




    //특정 packageNum으로 package data 출력(임시)
    public Package getPackage(long packageNum) {

        Optional<Package> packageData = packageRepository.findById(packageNum);

        return packageData.get();


    }

}

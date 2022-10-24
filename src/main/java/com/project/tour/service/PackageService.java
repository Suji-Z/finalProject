package com.project.tour.service;


import com.project.tour.domain.Package;
import com.project.tour.domain.PackageDate;
import com.project.tour.paging.CommonParams;
import com.project.tour.paging.Pagination;
import com.project.tour.repository.JejuPackageRepository;
import com.project.tour.repository.JejuSpecification;
import com.project.tour.repository.PackageRepository;
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
    public Page<Package> getSearchList(String location, String date, Integer count, String keyword, List<String> transport, List<Integer> period,
                                       Integer pricerangestr, Integer pricerangeend ,Pageable pageable) {

        /** 날짜 */
        Specification<Package> spec = Specification.where(JejuSpecification.greaterThanOrEqualToDeparture(date));

        /** 상세지역 */
        if (location == null || location.equals("")) {
            location = "제주";
            spec = spec.and(JejuSpecification.equalLocation1(location));
        } else {
            spec = spec.and(JejuSpecification.equalLocation2(location));
        }

        /** 여행객수 */
        if (count != null) {
            spec = spec.and(JejuSpecification.greaterThanOrEqualToRemaincount(count));
        }

        /** 키워드 */
        if (keyword != null) {
            spec = spec.and(JejuSpecification.equalKeyword(keyword));
        }

        /** 항공사 */
        if(transport!=null){
            spec = spec.and(JejuSpecification.equalTransport(transport));
        }

        /** 여행기한 */
        if(period !=null){
            spec = spec.and(JejuSpecification.equalPeriod(period));
        }

        /** 가격범위 */
        if(pricerangestr !=null || pricerangeend !=null){
            spec = spec.and(JejuSpecification.betweenPrice(pricerangestr,pricerangeend));
        }

        List<Package> searchPackage = jejuRepository.findAll(spec);

        //중복제거
        Iterator<Package> it = searchPackage.iterator();
        HashSet<Long> packageNum = new HashSet<>();
        while (it.hasNext()) {
            packageNum.add(it.next().getId());
        }


        //페이징처리
        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 :
                        pageable.getPageNumber() - 1,
                pageable.getPageSize(), Sort.by(sorts));

        return packageRepository.findByIdIn(packageNum, pageable);

    }



    //특정 packageNum으로 package data 출력(임시)
    public Package getPackage(long packageNum) {

        Optional<Package> packageData = packageRepository.findById(packageNum);

        return packageData.get();


    }

}

package com.project.tour.service;

import com.project.tour.domain.Package;
import com.project.tour.domain.PackageCreate;
import com.project.tour.domain.PackageDate;
import com.project.tour.repository.AdminPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminPackageService {

    private final AdminPackageRepository adminPackageRepository;

    //패키지 상품 업로드
    public void create(PackageCreate packageCreate, PackageDate packageDate) {

        Package aPackage = new Package();
        PackageDate packageDates = new PackageDate();

        aPackage.setPackageName(packageCreate.getPackageName());
        aPackage.setLocation(packageCreate.getLocation());
        aPackage.setHotelName(packageCreate.getHotelName());
        aPackage.setTransport(packageCreate.getTransport());
        packageDates.setA_price(packageCreate.getA_price());
        packageDates.setB_price(packageCreate.getB_price());
        packageDates.setC_price(packageCreate.getC_price());
        aPackage.setPackageInfo(packageCreate.getPackageInfo());
        aPackage.setCount(packageCreate.getCount());
        aPackage.setPostStart(packageCreate.getPostStart());
        aPackage.setPostEnd(packageCreate.getPostEnd());
        aPackage.setTravelPeriod(packageCreate.getTravelPeriod());
        aPackage.setKeyword(packageCreate.getKeyword());
        aPackage.setPreviewImage(packageCreate.getPreviewImage());
        aPackage.setDetailImage(packageCreate.getDetailImage());
        adminPackageRepository.save(aPackage);


    }

//    public void createprice(PackageCreate packageCreate){
//        PackageDate packageDate = new PackageDate();
//
//        packageDate.setA_price(packageCreate.getA_Price());
//        packageDate.setB_price(packageCreate.getB_Price());
//        packageDate.setC_price(packageCreate.getC_Price());
//
//        packageRepository.save(packageDate);
//    }

    //페이징
    public Page<Package> getList(Pageable pageable) {
        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 :
                        pageable.getPageNumber() - 1,
                pageable.getPageSize(), Sort.by(sorts)); //역순으로 정렬
        return adminPackageRepository.findAll(pageable);

    }

    //패키지 상품 삭제
    public void delete(Package aPackage) {
        adminPackageRepository.delete(aPackage);
    }

    //패키지 상품 조회수

    }



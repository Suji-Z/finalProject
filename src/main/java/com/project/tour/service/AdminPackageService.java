package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.repository.AdminPackageDateRepository;
import com.project.tour.repository.AdminPackageRepository;
import com.project.tour.repository.BookingRepository;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminPackageService {

    private final AdminPackageRepository adminPackageRepository;
    private final AdminPackageRepository adminPackageDateRepository;
    private final BookingRepository bookingRepository;

    private  final MemberRepository memberRepository;


    //패키지 상품 업로드
    public Package create(PackageCreate packageCreate,String image1,String image2) {

        Package aPackage = new Package();

        aPackage.setPackageName(packageCreate.getPackageName());
        aPackage.setLocation1(packageCreate.getLocation1());
        aPackage.setLocation2(packageCreate.getLocation2());
        aPackage.setHotelName(packageCreate.getHotelName());
        aPackage.setTransport(packageCreate.getTransport());
        aPackage.setPackageInfo(packageCreate.getPackageInfo());
        aPackage.setCount(packageCreate.getCount());
        aPackage.setPostStart(packageCreate.getPostStart());
        aPackage.setPostEnd(packageCreate.getPostEnd());
        aPackage.setTravelPeriod(packageCreate.getTravelPeriod());
        aPackage.setKeyword(packageCreate.getKeyword());
        aPackage.setPreviewImage(image1);
        aPackage.setDetailImage(image2);


        return adminPackageRepository.save(aPackage);
    }





    //페이징
    public Page<Package> getList(Pageable pageable){

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable= PageRequest.of(
                pageable.getPageNumber()<=0?0:
                        pageable.getPageNumber()-1,
                pageable.getPageSize(),Sort.by(sorts));

        return adminPackageRepository.findAll(pageable);
    }


    //패키지 상품 삭제
    public void deletePackage(Long id) {
        Package aPackage = adminPackageRepository.findById(id).get();
        adminPackageRepository.delete(aPackage);

    }

    public Package getPackage(Long id) {
        Optional<Package> op =  adminPackageRepository.findById(id); //하나의 데이터 읽어옴

        if(op.isPresent())
            return op.get();
        else
            throw new DataNotFoundException("데이터가 없습니다.");


    }

    //패키지 상품 수정
    public void modify(Package aPackage, PackageCreate packageCreate,String image1,String image2) {

        aPackage.setPackageName(packageCreate.getPackageName());
        aPackage.setLocation1(packageCreate.getLocation1());
        aPackage.setLocation2(packageCreate.getLocation2());
        aPackage.setHotelName(packageCreate.getHotelName());
        aPackage.setTransport(packageCreate.getTransport());
        aPackage.setPackageInfo(packageCreate.getPackageInfo());
        aPackage.setCount(packageCreate.getCount());
        aPackage.setPostStart(packageCreate.getPostStart());
        aPackage.setPostEnd(packageCreate.getPostEnd());
        aPackage.setTravelPeriod(packageCreate.getTravelPeriod());
        aPackage.setKeyword(packageCreate.getKeyword());
        aPackage.setPreviewImage(image1);
        aPackage.setDetailImage(image2);



        adminPackageRepository.save(aPackage);
    }

    public void userBookingCheck(UserBooking userBooking,UserBookingForm userBookingForm) {

        userBooking.setBookingStatus(1);

        bookingRepository.save(userBooking);

    }

    //회원 정보 수정
    public void updateUser(Member member, String name, String birth, String keyword,String phone,int point){
        member.setName(name);
        member.setBirth(birth);
        member.setKeyword(keyword);
        member.setPhone(phone);
        member.setPoint(point);

        memberRepository.save(member);
    }


    //회원탈퇴
    public void memberDelete(Member member) {
        memberRepository.delete(member);
    }

}
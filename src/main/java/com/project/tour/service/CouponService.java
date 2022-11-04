package com.project.tour.service;

import com.project.tour.domain.Coupon;
import com.project.tour.domain.Member;
import com.project.tour.repository.CouponRepository;
import com.project.tour.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    @Autowired
    private final CouponRepository couponRepository;
    private final MemberRepository memberRepository;

    //user 가지고 있는 쿠폰번호로 쿠폰 정보 검색
    public List<Coupon> getCoupon(String couponNum){

        List<Coupon> coupons = new ArrayList<>();

        if(couponNum!=null && couponNum!=""){

            //String[] str =couponNum.split(","); //1,2,3을 1 2 3으로 분리시켜 배열 저장
            List<String> strlist = Arrays.asList(couponNum.split(",")); //1,2,3을 1 2 3으로 분리시켜 리스트 저장
            coupons = couponRepository.findByIdIn(strlist);
        }

        return coupons;

    }

    //booking에서 선택한 쿠폰 정보 검색
    public Coupon getApplyCoupon(String couponNum){
        Optional<Coupon> coupon = couponRepository.findById(couponNum);
        return coupon.get();
    }

    public int getCouponRate(String couponNum){

        int couponRate = 0;
        if(!couponNum.equals("0")) {
            Optional<Coupon> coupon = couponRepository.findById(couponNum);
            couponRate = coupon.get().getCouponRate();
        }

        return couponRate;
    }

    //사용한 쿠폰 삭제
    public void deleteCoupon(String couponNum, Member member){

        int length = member.getCoupons().length();
        String nowCoupons = "";

        if(member.getCoupons().indexOf(couponNum)==length/2){
            //마지막 순서의 쿠폰을 사용함
            nowCoupons = member.getCoupons().substring(0,length-2);

        }else{ //중간 순서의 쿠폰 사용함
            nowCoupons = member.getCoupons().replaceAll(couponNum+",","");
        }

        member.setCoupons(nowCoupons);
        memberRepository.save(member);

    }

}

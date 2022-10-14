package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.repository.EstimateReplyRepository;
import com.project.tour.repository.PackageDateRepository;
import com.project.tour.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.callback.PasswordCallback;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class EstimateReplyService {

    @Autowired
    private final EstimateReplyRepository estimateReplyRepository;
    @Autowired
    private final PackageRepository packageRepository;
    @Autowired
    private final PackageDateRepository packageDateRepository;

    //답변하기
    public void create(EstimateInquiry inquiry,EstimateReplyForm replyForm){

        EstimateReply reply = new EstimateReply();

        reply.setTitle(replyForm.getTitle());
        reply.setContent(replyForm.getContent());
//        reply.setRecomPackage1(replyForm.getRecomPackage1());
//        reply.setRecomPackage2(replyForm.getRecomPackage2());
//        reply.setRecomPackage3(replyForm.getRecomPackage3());
        reply.setCreated(LocalDateTime.now());
        reply.setEstimateInquiry(inquiry);

        estimateReplyRepository.save(reply);
    }

    //답변수정하기
    public void modify(EstimateReply reply,EstimateReplyForm replyForm){

        reply.setTitle(replyForm.getTitle());
//        reply.setRecomPackage1(replyForm.getRecomPackage1());
//        reply.setRecomPackage2(replyForm.getRecomPackage2());
//        reply.setRecomPackage3(replyForm.getRecomPackage3());
        reply.setContent(replyForm.getContent());
        reply.setCreated(LocalDateTime.now());

        estimateReplyRepository.save(reply);
    }


    //답변삭제하기
    public void delete(EstimateReply reply){
        estimateReplyRepository.delete(reply);
    }

    //id를 통한 답변 검색
    public EstimateReply getArticle(Long id) {

        Optional<EstimateReply> reply = estimateReplyRepository.findById(id);

        if(reply.isPresent())
            return reply.get();
        else
            throw new DataNotFoundException("확인할수 없는 게시물 입니다.");
    }

    public List<Package> getPackages(EstimateInquiry inquiry){

        List<PackageDate> packageDate;
        List<Package> packages;

        /** 1. 출발일이 같을때 */
        int startDay = Integer.parseInt(inquiry.getStartDay().replace("-",""));
        String startDay1,startDay2;
        if (inquiry.getFlexibleDay()) {
            startDay1 = String.valueOf(startDay - 7);
            startDay2 = String.valueOf(startDay + 7);
        } else {
            startDay1 = String.valueOf(startDay);
            startDay2 = String.valueOf(startDay);
        }

        /** 2.인원수만큼 잔여수량이 있을때 */
        int total = inquiry.getACount()+ inquiry.getBCount()+ inquiry.getCCount();

        /** 3. 가격이 범위안에 있을때 */
        String price = inquiry.getPrice();
        int minPrice, maxPrice;
        if (price.contains("-")) {
            String[] splitPrice = price.split("-", 2);
            minPrice = Integer.parseInt(splitPrice[0]);
            maxPrice = Integer.parseInt(splitPrice[1]);
            packageDate = packageDateRepository.findByDepartureBetweenAndApriceBetweenAndRemaincountGreaterThanEqual(startDay1, startDay2, minPrice, maxPrice,total);

        }else{
            minPrice = Integer.parseInt(price);
            packageDate = packageDateRepository.findByDepartureBetweenAndApriceGreaterThanEqualAndRemaincountGreaterThanEqual(startDay1, startDay2, minPrice,total);
        }

        System.out.println(packageDate.size());

        /** 중복제거 */
        Iterator<PackageDate> it = packageDate.iterator();
        Set<Long> packageNum = new HashSet<>();
        while(it.hasNext()){
            System.out.println(it.next().getPackages().getId());
            packageNum = Collections.singleton(it.next().getPackages().getId()); /** 싱글톤 */
        }

        /** 4. 지역이 같을때 */
        String location2 = inquiry.getLocation2();

        packages = packageRepository.findByLocation2AndIdIn(location2,packageNum);

        return packages;
    }
}

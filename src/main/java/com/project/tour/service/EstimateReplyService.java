package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.repository.EstimateReplyRepository;
import com.project.tour.repository.EstimateSearchRepository;
import com.project.tour.repository.PackageDateRepository;
import com.project.tour.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EstimateReplyService {

    @Autowired
    private final EstimateReplyRepository estimateReplyRepository;
    @Autowired
    private final PackageRepository packageRepository;
    @Autowired
    private final EstimateSearchRepository searchRepository;

    //답변하기
    public void create(EstimateInquiry inquiry,EstimateReplyForm replyForm){

        EstimateReply reply = new EstimateReply();

        reply.setTitle(replyForm.getTitle());
        reply.setContent(replyForm.getContent());
        reply.setRecomPackage(replyForm.getRecomPackage());
        reply.setCreated(LocalDateTime.now());
        reply.setEstimateInquiry(inquiry);

        estimateReplyRepository.save(reply);
    }

    //답변수정하기
    public void modify(EstimateReply reply,EstimateReplyForm replyForm){

        reply.setTitle(replyForm.getTitle());
        reply.setRecomPackage(replyForm.getRecomPackage());
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

    //답변에 출력할 패키지 정보 가져오기
    public List<Package> recom(String[] packages){

        List<Long> packageid = new ArrayList<>();
        for(int i = 0; i<packages.length; i++){
            System.out.println(packages[i]);
            System.out.println(Long.parseLong(packages[i]));
            packageid.add(Long.parseLong(packages[i]));
        }

        return packageRepository.findByIdIn(packageid);
    }

    /** Querydsl 사용해서 동적검색 */
    public List<EstimateSearchDTO> getPackages(EstimateInquiry inquiry) throws ParseException{

        /** 1. 여행일수 */
        String startDay = inquiry.getStartDay().replaceAll("-","");
        String endDay = inquiry.getEndDay().replaceAll("-","");

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date start = format.parse(startDay);
        Date end = format.parse(endDay);

        Long days = (end.getTime() - start.getTime()) / (24 * 60 * 60 * 1000);
        Integer travelPeriod = (int) (long) days;

        /** 2. 출발일 */
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        //유동적 출발일선택시 flxStart1,2 사용 : 미선택시 startDay 사용
        String flxStart1 =null;
        String flxStart2 = null;
        if (inquiry.getFlexibleDay()) {
            cal1.add(Calendar.DATE, -7);
            flxStart1 = String.valueOf(format.format(cal1.getTime()));
            cal2.add(Calendar.DATE, 7);
            flxStart2 = String.valueOf(format.format(cal2.getTime()));
            startDay=null;
        }

        /** 3.인원수만큼 잔여수량이 있을때 */
        int totcount = inquiry.getACount()+ inquiry.getBCount()+ inquiry.getCCount();

        /** 4. 가격이 범위안에 있을때 */

        Integer price = null;
        Integer minPrice = null;
        Integer maxPrice = null;
        if (inquiry.getPrice().contains("-")) {
            String[] splitPrice = inquiry.getPrice().split("-", 2);
            minPrice = Integer.parseInt(splitPrice[0]);
            maxPrice = Integer.parseInt(splitPrice[1]);
        }else{
            price = Integer.parseInt(inquiry.getPrice());
       }

        /** 5. 지역이 같을때 */
        String location2 = inquiry.getLocation2();

        //검색조건 객체생성
        EstimateSearchCondition condition = new EstimateSearchCondition();

        condition.setTravelPeriod(travelPeriod);
        condition.setStartday(startDay);
        condition.setFlxstartday1(flxStart1);
        condition.setFlxstartday2(flxStart2);
        condition.setPrice(price);
        condition.setMinPrice(minPrice);
        condition.setMaxPrice(maxPrice);
        condition.setRemaincount(totcount);
        condition.setLcoation2(location2);

        List<EstimateSearchDTO> packages = searchRepository.searchByWhere(condition);

        return packages;
    }


}

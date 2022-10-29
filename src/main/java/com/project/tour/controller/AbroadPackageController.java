package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.*;
import com.project.tour.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/package")
public class AbroadPackageController {

    private final PackageService packageService;

    private final MemberService memberService;

    private final PackageDateService packagedateService;

    private final WishListService wishListService;

    private final ShortReviewService shortReviewService;



    private final Logger log = LoggerFactory.getLogger(getClass());


    @ModelAttribute("transports")
    public Map<String, String> transport() {
        Map<String, String> transport = new LinkedHashMap<>();
        transport.put("대한항공", "대한항공");
        transport.put("아시아나항공", "아시아나항공");
        transport.put("제주항공", "제주항공");
        transport.put("진에어", "진에어");
        transport.put("티웨이", "티웨이");
        return transport;
    }

    @ModelAttribute("travelPeriods")
    public Map<String, String> travelPeriod() {
        Map<String, String> travelPeriod = new LinkedHashMap<>();
        travelPeriod.put("1,2", "2일 ~ 3일");
        travelPeriod.put("3,4", "4일 ~ 5일");
        travelPeriod.put("5,6", "6일 ~ 7일");
        travelPeriod.put("7,8,9", "8일 ~ 10일");
        return travelPeriod;
    }





    @GetMapping("/abroad")
    public String packagelist(@RequestParam(value = "location1", required = false) String location1,
                              @RequestParam(value = "location2", required = false) String location2,
                              @RequestParam(value = "date", required = false) String date,
                              @RequestParam(value = "totcount", required = false) Integer count,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "transports", required = false) String transports,
                              @RequestParam(value = "travelPeriods", required = false) String travelPeriods,
                              @RequestParam(value = "pricerangestr", required = false) Integer pricerangestr,
                              @RequestParam(value = "pricerangeend", required = false) Integer pricerangeend,
                              @RequestParam(value = "hitCount", required = false) Integer hitCount,
                              Model model, @PageableDefault(size = 5) Pageable pageable,
                              SearchForm searchForm) {

        //여행객 버튼 기본값 0출력
        if(searchForm.getTotcount() == null || searchForm.getTotcount().equals("")){
            searchForm.setTotcount(0);
        }

        //날짜가 선택되지 않았을때
        if (date == null || date.equals("")) {
            date = null;
        } else {//날짜포맷 db와 맞추기
            date = date.replaceAll("-", "");
        }

        //항공사 다중선택시
        List<String> transport = null;
        if (transports == null || transports.equals("")) {
            transports=null;
        } else {
            transport = Arrays.asList(transports.split(","));
        }

        //여행기간
        List<Integer> period = null;
        List<String> periods;
        if(travelPeriods==null || travelPeriods.equals("")){
        }else {
            period = new ArrayList<>();
            periods = Arrays.asList(travelPeriods.split(","));

            Iterator<String> it = periods.iterator();

            while(it.hasNext()){
                period.add(Integer.parseInt(it.next()));
            }
        }


        log.info("DATE : " + date);
        log.info("LOCATION1 : " + location1);
        log.info("LOCATION2 : " + location2);
        log.info("COUNT : " + String.valueOf(count));
        log.info("KEYWORD : " + keyword);
        log.info("TRANSPORTS : " + transports);
        log.info("TRAVELPERIOD : " + travelPeriods);



        Page<PackageSearchDTO> paging = packageService.getSearchListabroad(location1,location2, date, count,keyword,transport,period,pricerangestr,pricerangeend,hitCount,pageable);



        model.addAttribute("paging", paging);
        model.addAttribute("searchForm", searchForm);

        return "abroadPackage/packagelist";
    }





    /*상세 페이지*/

    @RequestMapping("/{id}")
    public String packagedetail(Model model, @PathVariable("id") Long id, Principal principal,@LoginUser SessionUser user,
                                ShortReviewForm shortReviewForm){

        Package packages = packageService.getPackage(id);


        //위시리스트 (코드 정리 요망)

        Member member;
        String email;
        String name;

        if(principal==null && user==null){ //로그아웃

            System.out.println("로그아웃이다");
            model.addAttribute("url","/assets/img/icon/ReviewHeart1.png");
            model.addAttribute("recommendStatus", 0);
            model.addAttribute("loginStatus","n");

        }else if(user!=null){ //간편로그인
            System.out.println("간편로그인이다");

            member = memberService.getName(user.getEmail());

            email = user.getEmail();
            name = user.getName();


            model.addAttribute("email",email);
            model.addAttribute("name",name);

            Long id2 = member.getId();

            //결제완료된 부킹리스트 가져오기
            int status = 2; // 0:예약확인중 1:결제대기중 2:결제완료

            List<UserBooking> bookingShortReview = shortReviewService.getBookingShortReview(id2, status, packages);
            System.out.println(bookingShortReview.size());


            model.addAttribute("bookingShortReview", bookingShortReview);

            int wish = wishListService.getWishList(id2,id);

            if(wish==1){
                model.addAttribute("url","/assets/img/icon/ReviewHeart2.png");
                model.addAttribute("recommendStatus", 1);
                model.addAttribute("loginStatus","y");


            }else{
                model.addAttribute("url","/assets/img/icon/ReviewHeart1.png");
                model.addAttribute("recommendStatus", 0);
                model.addAttribute("loginStatus","y");
            }

        }else if (principal!=null){ //일반회원
            System.out.println("일반회원이다");

            member = memberService.getName(principal.getName());

            email = member.getEmail();
            name = member.getName();

            model.addAttribute("email",email);
            model.addAttribute("name",name);

            Long id2 = member.getId();

            //결제완료된 부킹리스트 가져오기
            int status = 2; // 0:예약확인중 1:결제대기중 2:결제완료

            List<UserBooking> bookingShortReview = shortReviewService.getBookingShortReview(id2, status,packages);
            System.out.println(bookingShortReview.size());


            model.addAttribute("bookingShortReview", bookingShortReview);


            System.out.println(id2);
            System.out.println(id);

            int wish = wishListService.getWishList(id2,id);

            System.out.println(wish);

            if(wish==1){
                model.addAttribute("url","/assets/img/icon/ReviewHeart2.png");
                model.addAttribute("recommendStatus", 1);
                model.addAttribute("loginStatus","y");

            }else{
                model.addAttribute("url","/assets/img/icon/ReviewHeart1.png");
                model.addAttribute("recommendStatus", 0);
                model.addAttribute("loginStatus","y");
            }

        }

        //hitCount 증가
        int hitCount = packages.getHitCount()+1;
        packageService.updateHitCount(hitCount,id);

        List<ShortReview> shortReviewList = shortReviewService.getshortReviewList(id);
        Integer size = shortReviewList.size();

        Double sum = 0.0;

        Iterator<ShortReview> it = shortReviewList.iterator();


        while(it.hasNext()){
            
            sum += it.next().getScore();
        }

        System.out.println("합계" + sum);


        model.addAttribute("shortReviewList", shortReviewList);
        model.addAttribute("size",size);
        model.addAttribute("packages",packages);
        model.addAttribute("shortReviewForm",shortReviewForm);
        model.addAttribute("bookingform",new BookingDTO());
        model.addAttribute("sum",sum);


        return "abroadPackage/packagedetail";




    }








}

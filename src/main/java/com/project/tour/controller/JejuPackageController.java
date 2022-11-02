package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.PackageService;
import com.project.tour.service.PackageDateService;
import com.project.tour.service.WishListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/package")
public class JejuPackageController {

    @Autowired
    private final PackageService packageService;
    @Autowired
    private final PackageDateService packagedateService;

    private final MemberService memberService;

    private final WishListService wishListService;

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

    /**
     * 전체리스트
     */
    @GetMapping("/jeju")
    public String packagelist(@RequestParam(value = "location1", required = false) List<String> location1,
                              @RequestParam(value = "location2", required = false) String location2,
                              @RequestParam(value = "date", required = false) String date,
                              @RequestParam(value = "totcount", required = false) Integer count,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "transports", required = false) String transports,
                              @RequestParam(value = "travelPeriods", required = false) String travelPeriods,
                              @RequestParam(value = "pricerangestr", required = false) Integer pricerangestr,
                              @RequestParam(value = "pricerangeend", required = false) Integer pricerangeend,
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
        log.info("PRICERANGESTR : " + String.valueOf(pricerangestr));
        log.info("PRICERANGEEND : " + String.valueOf(pricerangeend));

        Page<PackageSearchDTO> paging = packageService.getSearchList(location1,location2, date, count,keyword,transport,period,pricerangestr,pricerangeend,pageable);

        model.addAttribute("paging", paging);
        model.addAttribute("searchForm", searchForm);

        return "jejuPackage/packagelist";
    }

    /**
     * 상세페이지
     */
    @GetMapping("/jeju/{id}")
    public String packagedetail(@PathVariable("id") Long id, Model model,Principal principal,@LoginUser SessionUser user) {

        Package apackage = packageService.getPackage(id);

        //위시리스트 (코드 정리 요망)

        Member member;

        if(principal==null && user==null){ //로그아웃

            System.out.println("로그아웃이다");
            model.addAttribute("url","/assets/img/icon/ReviewHeart1.png");
            model.addAttribute("recommendStatus", 0);
            model.addAttribute("loginStatus","n");


        }else if(user!=null){ //간편로그인
            System.out.println("간편로그인이다");

            member = memberService.getName(user.getEmail());

            Long id2 = member.getId();

            int wish = wishListService.getWishList(id2,id);

            if(wish==1){
                model.addAttribute("url","/assets/img/icon/ReviewHeart2.png");
                model.addAttribute("recommendStatus", 1);
                model.addAttribute("loginStatus","y");


            }else{
                model.addAttribute("rul","/assets/img/icon/ReviewHeart1.png");
                model.addAttribute("recommendStatus", 0);
                model.addAttribute("loginStatus","y");
            }

        }else if (principal!=null){ //일반회원
            System.out.println("일반회원이다");

            member = memberService.getName(principal.getName());

            Long id2 = member.getId();
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


        model.addAttribute("package", apackage);
        model.addAttribute("bookingform", new BookingDTO());
        return "jejuPackage/packagedetail";
    }

    /**
     * 상세페이지 여행날짜별 가격출력
     */
    @GetMapping("/dateprice")
    @ResponseBody
    public HashMap<String, Object> datecountprice(@RequestParam("acount") Integer acount, @RequestParam("ccount") Integer ccount,
                                                  @RequestParam("bcount") Integer bcount, @RequestParam("date") String date,
                                                  @RequestParam("packagenum") Long packagenum) {

        HashMap<String, Object> priceInfo = new HashMap<String, Object>();
        int aprice, bprice, cprice, dcaprice, dcbprice, dccprice;

        date = date.replaceAll("-", "");

        log.info(date.getClass().getTypeName());

        /* 해당 날짜에 어른/아이/유아 타입별 가격*/
        PackageDate getPackage = packagedateService.getPrice(packagenum, date);
        Integer discount = getPackage.getDiscount();
        
        /* 잔여좌석 여부 */
        Integer remaincount = getPackage.getRemaincount();
        Integer totcount = acount + bcount + ccount;

        /** 정가 */
        aprice = getPackage.getAprice() * acount;
        bprice = getPackage.getBprice() * bcount;
        cprice = getPackage.getCprice() * ccount;

        if (getPackage.getDiscount() == null) {
        } else {/** 할인가 */

            dcaprice = (int) (aprice - (aprice * (discount * 0.01)));
            dcbprice = (int) (bprice - (bprice * (discount * 0.01)));
            dccprice = (int) (cprice - (cprice * (discount * 0.01)));
            priceInfo.put("dcaprice", dcaprice);
            priceInfo.put("dcbprice", dcbprice);
            priceInfo.put("dccprice", dccprice);
        }

        //json형태 데이터로 넘기기
        priceInfo.put("acount", acount);
        priceInfo.put("aprice", aprice);
        priceInfo.put("ccount", ccount);
        priceInfo.put("cprice", cprice);
        priceInfo.put("bcount", bcount);
        priceInfo.put("bprice", bprice);
        priceInfo.put("discount", discount);
        priceInfo.put("remaincount", remaincount);
        priceInfo.put("totcount", totcount);

        return priceInfo;
    }

    //위시리스트
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/jeju/wishList")
    public @ResponseBody HashMap<String,Object> reviewVote(@RequestParam("recommendStatus") int recommendStatus,
                                                           Principal principal, @RequestParam("id") Long id, @LoginUser SessionUser user){

        Package packages = packageService.getPackage(id);

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        if(recommendStatus==1){
            wishListService.wishList(packages,member);
        }else if(recommendStatus==0){
            wishListService.deleteWish(member.getId(),id);
        }

        HashMap<String,Object> recommendInfo = new HashMap<>();
        recommendInfo.put("recommendStatus", recommendStatus);

        return recommendInfo;

    }

}

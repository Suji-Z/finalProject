package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.PackageDateService;
import com.project.tour.service.PackageService;
import com.project.tour.service.ShortReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    private final ShortReviewService shortReviewService;

    private final PackageDateService packagedateService;


    private final Logger log = LoggerFactory.getLogger(getClass());

    /** 전체리스트 */
    @GetMapping("/abroad")
    public String packagelist(@RequestParam(value = "location", required = false) String location,
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
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            date = today.format(formatter);

        } else {//날짜포맷 db와 맞추기
            date = date.replaceAll("-", "");
        }

        if(keyword==null || keyword.equals("")){
            keyword=null;
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

        //가격범위
        if(pricerangestr==null || pricerangestr.equals("") && pricerangeend==null || pricerangeend.equals("")){
            pricerangestr =null;
            pricerangeend = null;
        }else{
            log.info("pricerangestr : " + pricerangestr);
            log.info("pricerangeend : " + pricerangeend);
        }

        log.info("DATE : " + date);
        log.info("LOCATION : " + location);
        log.info("COUNT : " + String.valueOf(count));
        log.info("KEYWORD : " + keyword);
        log.info("TRANSPORTS : " + transports);
        log.info("TRAVELPERIOD : " + travelPeriods);



        Page<PackageSearchDTO> paging = packageService.getSearchListabroad(location, date, count,keyword,transport,period,pricerangestr,pricerangeend,pageable);

        model.addAttribute("paging", paging);
        model.addAttribute("searchForm", searchForm);

        return "abroadPackage/packagelist";
    }

//    @GetMapping("/abroad")
//    public Map<String, Object> findAll(final CommonParams params) {
//        return PackageService.findAll(params);
//    }




    /*상세 페이지*/
    @GetMapping("/{id}")
    public String packagedetail(Model model, @PathVariable("id") Long id, Principal principal,@LoginUser SessionUser user){

        Package packages = packageService.getPackage(id);
//        packageService.updateHitCount(id);

        String email;
        String name;



        if(memberService.existByEmail(principal.getName())){

            Member userName = memberService.getName(principal.getName());
            email = userName.getEmail();
            name = userName.getName();


        }else {

            email = user.getEmail();
            name = user.getName();
        }


        model.addAttribute("packages",packages);
        model.addAttribute("shortReviewForm",new ShortReviewForm());
        model.addAttribute("shortReview",new ShortReview());
        model.addAttribute("email",email);
        model.addAttribute("name",name);
        model.addAttribute("bookingform", new BookingDTO());


        return "abroadPackage/packagedetail";
    }


    /**
     * 상세페이지 여행날짜별 가격출력
     */
//    @GetMapping("/dateprice")
//    @ResponseBody
//    public HashMap<String, Object> datecountprice(@RequestParam("acount") Integer acount, @RequestParam("ccount") Integer ccount,
//                                                  @RequestParam("bcount") Integer bcount, @RequestParam("date") String date,
//                                                  @RequestParam("packagenum") Long packagenum) {
//
//        HashMap<String, Object> priceInfo = new HashMap<String, Object>();
//        int aprice, bprice, cprice, dcaprice, dcbprice, dccprice;
//
//        date = date.replaceAll("-", "");
//
//        log.info(date.getClass().getTypeName());
//
//        /* 해당 날짜에 어른/아이/유아 타입별 가격*/
//        PackageDate getPackagePrice = packagedateService.getPrice(packagenum, date);
//        Integer discount = getPackagePrice.getDiscount();
//
//        /** 정가 */
//        aprice = getPackagePrice.getAprice() * acount;
//        bprice = getPackagePrice.getBprice() * bcount;
//        cprice = getPackagePrice.getCprice() * ccount;
//
//        if (getPackagePrice.getDiscount() == null) {
//
//        } else {/** 할인가 */
//
//            dcaprice = (int) (aprice - (aprice * (discount * 0.01)));
//            dcbprice = (int) (bprice - (bprice * (discount * 0.01)));
//            dccprice = (int) (cprice - (cprice * (discount * 0.01)));
//            priceInfo.put("dcaprice", dcaprice);
//            priceInfo.put("dcbprice", dcbprice);
//            priceInfo.put("dccprice", dccprice);
//        }
//
//        //json형태 데이터로 넘기기
//        priceInfo.put("acount", acount);
//        priceInfo.put("aprice", aprice);
//        priceInfo.put("ccount", ccount);
//        priceInfo.put("cprice", cprice);
//        priceInfo.put("bcount", bcount);
//        priceInfo.put("bprice", bprice);
//        priceInfo.put("discount", discount);
//
//        return priceInfo;
//    }





    //텍스트 리뷰 작성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(@Valid ShortReviewForm shortReviewForm, BindingResult bindingResult, @PathVariable("id") Long id,
                         @PageableDefault Pageable pageable, Principal principal, @LoginUser SessionUser user)  throws IOException {

        if (bindingResult.hasErrors()){
            return "abroadPackage/packagedetail";
        }


        Member userName = memberService.getName(user.getName());

        Package packages = packageService.getPackage(id);

        ShortReview shortReview = shortReviewService.create(shortReviewForm.getContent(),shortReviewForm.getScore(),userName, packages);


        return "redirect:/package/{id}";
    }


   // 텍스트 리뷰 리스트
//    @RequestMapping("/reviewlist/{id}")
//    public String reviewlist(Model model, Principal principal,
//                             @PageableDefault Pageable pageable,Package packages,ShortReview shortReview){
//
//
////        packages = packageService.getPackage(packages.getId());
////        Long packageNum = packages.getId();
//
//        List<ShortReview> shortReviews = shortReviewService.getshortReview(packages.getId());
////        Long packageNum = shortReview.getId();
//
//
//
////        List<ShortReview> shortReviews = shortReviewService.getshortReview(packageNum);
//
//        model.addAttribute("shortReviews",shortReviews);
//
//
//        return "abroadPackage/packagedetail";
//
//    }


}

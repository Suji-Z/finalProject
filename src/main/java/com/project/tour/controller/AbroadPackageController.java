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

    private final ShortReviewService shortReviewService;

    private final PackageDateService packagedateService;

    private final ShortReviewReplyService shortReviewReplyService;


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
                              @RequestParam(value = "hitCount", required = false) Integer hitCount,
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



        Page<PackageSearchDTO> paging = packageService.getSearchListabroad(location, date, count,keyword,transport,period,pricerangestr,pricerangeend,hitCount,pageable,searchForm);

        model.addAttribute("paging", paging);
        model.addAttribute("searchForm", searchForm);

        return "abroadPackage/packagelist";
    }




    /**
     * 상세페이지 여행날짜별 가격출력
     */
    @GetMapping("/dateprice/abroad")
    @ResponseBody
    public HashMap<String, Object> datecountpriceabroad(@RequestParam("acount") Integer acount, @RequestParam("ccount") Integer ccount,
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



    /*상세 페이지*/
    @RequestMapping("/{id}")
    public String packagedetail(Model model, @PathVariable("id") Long id, Principal principal,@LoginUser SessionUser user,
                                ShortReviewForm shortReviewForm){

        Package packages = packageService.getPackage(id);

        int hitCount = packages.getHitCount()+1;
        packageService.updateHitCount(hitCount,id);


        model.addAttribute("package",packages);
        model.addAttribute("shortReviewForm",shortReviewForm);
        model.addAttribute("bookingform",new BookingDTO());


        return "abroadPackage/packagedetail";




    }








}

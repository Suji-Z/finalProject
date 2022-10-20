package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.service.PackageService;
import com.project.tour.service.PackageDateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/jeju")
public class JejuPackageController {

    @Autowired
    private final PackageService packageService;
    @Autowired
    private final PackageDateService packagedateService;


    /**
     * 전체리스트
     */
    @GetMapping("/list")
    public String packagelist(@RequestParam(value = "location", required = false) String location,
                              @RequestParam(value = "date", required = false) String date,
                              @RequestParam(value = "totcount", required = false) Integer count,
                              @RequestParam(value = "keyword", required = false) String keyword,
                              @RequestParam(value = "transport", required = false) String transport,
                              @RequestParam(value = "pricerangestr", required = false) Integer pricerangestr,
                              @RequestParam(value = "pricerangeend", required = false) Integer pricerangeend,
                              @RequestParam(value = "reviewstar", required = false) Double reviewstar,
                              @RequestParam(value = "travelPeriod", required = false) Integer travelPeriod,
                              Model model, @PageableDefault(size = 5) Pageable pageable,
                              SearchForm searchForm) {

        //여행객 버튼 기본값 0출력
        if(searchForm.getTotcount() ==null || searchForm.getTotcount().equals("")){
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

        //항공사 다중선택시
        List<String> transports = null;
        if (searchForm.getTransport() != null) {
            transports = Arrays.asList(searchForm.getTransport().split(","));
        }

        log.info(date);
        log.info(location);
        log.info(String.valueOf(count));
        log.info(searchForm.getKeyword());
        log.info(searchForm.getTransport());

        Page<Package> paging = packageService.getSearchList(location, date, count,keyword,transports,pageable);

        model.addAttribute("paging", paging);
        if(searchForm==null){ //검색전 최초로딩시
            model.addAttribute("searchForm", new SearchForm());
        }
        else {//검색후 검색데이터 유지
            model.addAttribute("searchForm", searchForm);
        }
        return "jejuPackage/packagelist";
    }

    /**
     * 상세페이지
     */
    @GetMapping("/{id}")
    public String packagedetail(@PathVariable("id") Long id, Model model) {

        Package apackage = packageService.getPackage(id);

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
        PackageDate getPackagePrice = packagedateService.getPrice(packagenum, date);
        Integer discount = getPackagePrice.getDiscount();

        /** 정가 */
        aprice = getPackagePrice.getAprice() * acount;
        bprice = getPackagePrice.getBprice() * bcount;
        cprice = getPackagePrice.getCprice() * ccount;

        if (getPackagePrice.getDiscount() == null) {

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

        return priceInfo;
    }

}

package com.project.tour.controller;

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
    public String packagelist(Model model, @PageableDefault Pageable pageable) {

        Page<Package> paging = packageService.getList(pageable);

        model.addAttribute("paging", paging);

        return "jejuPackage/packagelist";
    }

    /**
     * 지역별 리스트
     */
    @GetMapping("/{location}")
    public String packageLocation(Model model, @PathVariable("location") String location, @PageableDefault Pageable pageable) {

        Page<Package> paging = packageService.getLocationList(location, pageable);

        model.addAttribute("paging", paging);

        return "jejuPackage/packagelist";
    }


    /**
     * 지역, 출발예정일, 여행객 상단바 검색버튼
     */

    @PostMapping("/search")
    public String searchPackage(@RequestParam(value = "location", required = false) String location,
                                @RequestParam(value = "date", required = false) String date,
                                @RequestParam(value = "counthidden", required = false) Integer count,
                                @PageableDefault Pageable pageable, Model model) {

        log.info(location);
        log.info(date);
        log.info(String.valueOf(count));

        /** 인원값이 null일때 0으로 고정 */
        if (count == null || count.equals("0")) {
            count = 0;
        }

        if (location == null || location.equals("")) {
            location = "제주";
            Page<Package> paging = packageService.getdatecountsearchList(location, date, count, pageable);
            model.addAttribute("paging", paging);
            return "jejuPackage/packagelist";
        }

        Page<Package> paging = packageService.getfullsearchList(location, date, count, pageable);

        model.addAttribute("paging", paging);

        return "jejuPackage/packagelist";
    }


    /**
     * 상세페이지
     */
    @GetMapping("/{location}/{id}")
    public String packagedetail(@PathVariable("location") String location, @PathVariable("id") Long id, Model model) {

        Package apackage = packageService.getPackage(id);

        //PackageDate packageDate = packagedateService.getPackageDate2(id);

        model.addAttribute("package", apackage);
        //model.addAttribute("packageDate",packageDate);

        return "jejuPackage/packagedetail";
    }

    /**
     * 상세페이지 여행날짜별 가격출력

    @GetMapping("/dateprice")
    public PackageDate packagedetail(Model model) {




        return packagedateService.get
    }
     */
}

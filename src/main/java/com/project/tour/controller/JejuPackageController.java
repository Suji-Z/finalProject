package com.project.tour.controller;

import com.project.tour.domain.Package;
import com.project.tour.service.PackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/jeju")
public class JejuPackageController {

    @Autowired
    private final PackageService packageService;

    @GetMapping("/list")
    public String packagelist(Model model, @PageableDefault Pageable pageable) {

        Page<Package> paging = packageService.getList(pageable);

        model.addAttribute("paging",paging);

        return "jejuPackage/packagelist";
    }

    @GetMapping("/{location}")
    public String packageLocation(Model model,@PathVariable("location") String location, @PageableDefault Pageable pageable) {

        Page<Package> paging = packageService.getLocationList(location,pageable);

        model.addAttribute("paging",paging);

        return "jejuPackage/packagelist";
    }

    @GetMapping("/{location}/{id}")
    public String packagedetail(@PathVariable("location") String location,@PathVariable("id") Long id,Model model){

        Package apackage = packageService.getPackage(id);

        model.addAttribute("package",apackage);

        return "jejuPackage/packagedetail";
    }

}

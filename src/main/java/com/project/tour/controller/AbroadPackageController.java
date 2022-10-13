package com.project.tour.controller;

import com.project.tour.domain.Package;
import com.project.tour.service.PackageService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/abroad")
public class AbroadPackageController {

    private final PackageService packageService;

    @GetMapping("/list")
    public String packagelist(Model model,@PageableDefault Pageable pageable){

        Page<Package> paging = packageService.getList(pageable);

        model.addAttribute("paging",paging);

        return "abroadPackage/packagelist";
    }

    @GetMapping("/detail/{id}")
    public String packagedetail(Model model, @PathVariable("id") Long id){

        Package apackage = packageService.getPackage(id);

        model.addAttribute("package",apackage);

        return "abroadPackage/packagedetail";
    }
}

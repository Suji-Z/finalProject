package com.project.tour.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/abroad")
public class AbroadPackageController {

    @GetMapping("/list")
    public String packagelist(){

        return "abroadPackage/packagelist";
    }

    @GetMapping("/detail")
    public String packagedetail(){

        return "abroadPackage/packagedetail";
    }
}

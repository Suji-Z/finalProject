package com.project.tour.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jeju")
public class JejuPackageController {

    @GetMapping("/list")
    public String packagelist() {
        return "jejuPackage/packagelist";
    }

    @GetMapping("/detail")
    public String packagedetail() {
        return "jejuPackage/packagedetail";
    }

}

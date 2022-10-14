package com.project.tour.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jeju")
public class JejuPackageController {

    @GetMapping("/list")
    public String packagelist() {
        return "jejuPackage/packagelist";
    }

    @GetMapping("/list/{location}")
    public String packageLocation(@PathVariable("location") String location2) {



        return "jejuPackage/packagelist";
    }

    @GetMapping("/{location}/detail")
    public String packagedetail(@PathVariable("location") String location2){



        return "jejuPackage/packagedetail";
    }

}

package com.project.tour.controller;

import com.project.tour.domain.Package;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final PackageService packageService;


    @GetMapping("/")
    public String main(Model model, @LoginUser SessionUser user,
                       @RequestParam(value = "keyword") String keyword) {

        //model.addAttribute("posts",postsService.findAllDesc());

        if(user!=null){
            model.addAttribute("email",user.getEmail());
            model.addAttribute("name",user.getName());
        }

        Package packages =  packageService.getKeyword(keyword);


        model.addAttribute("theme",packages);
        model.addAttribute("keyword",packages.getKeyword());

        return "main";
    }
}
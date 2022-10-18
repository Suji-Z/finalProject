package com.project.tour.controller;

import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String main(Model model, @LoginUser SessionUser user) {

        //model.addAttribute("posts",postsService.findAllDesc());

        if(user!=null){
            model.addAttribute("email",user.getEmail());
            model.addAttribute("name",user.getName());
        }


        return "main";
    }
}
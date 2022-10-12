package com.project.tour.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    @GetMapping(value = "/write")
    public String write1(){

        return "review/review_write";

    }

    @PostMapping(value = "/write")
    public String write2(){

        return "redirect:/review/review_Abroad_List";

    }

    @GetMapping(value = "/update")
    public String update1(){

        return "review/review_write";

    }

    @PostMapping(value = "/update")
    public String update2(){

        return "redirect:/review/review_article";

    }

    @GetMapping (value = "/article")
    public String article(){

        return "review/review_article";

    }

    @GetMapping (value = "/abroadList")
    public String abroadList(){

        return "review/review_Abroad_List";

    }

    @GetMapping (value = "/jejuList")
    public String jejuList(){

        return "review/review_Jeju_List";

    }

    @GetMapping(value = "/delete")
    public String delete(){

        return "redirect:/review/review_abroadList";

    }



}

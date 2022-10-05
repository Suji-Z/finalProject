package com.project.tour.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/estimate")
public class EstimateController {

    @GetMapping("/list")
    public String estimate() {
        return "layout/estimateList";
    }

    @GetMapping("/upload")
    public String insert() {
        return "layout/estimateInquiry";
    }

    @GetMapping("/inquiry/article")
    public String article() {
        return "layout/estimateInquiryArticle";
    }

    @GetMapping("/reply")
    public String estimatereply() {
        return "layout/estimateReply";
    }

    @GetMapping("/reply/article")
    public String estimatereplyarticle() {
        return "layout/estimateReplyArticle";
    }

}

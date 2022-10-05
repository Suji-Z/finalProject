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
        return "estimate/estimateList";
    }

    @GetMapping("/upload")
    public String insert() {
        return "estimate/estimateInquiry";
    }

    @GetMapping("/inquiry/article")
    public String article() {
        return "estimate/estimateInquiryArticle";
    }

    @GetMapping("/reply")
    public String estimatereply() {
        return "estimate/estimateReply";
    }

    @GetMapping("/reply/article")
    public String estimatereplyarticle() {
        return "estimate/estimateReplyArticle";
    }

}

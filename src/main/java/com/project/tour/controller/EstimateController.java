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
    public String estimateList() {
        return "estimate/estimateList";
    }

    @GetMapping("/upload")
    public String estimateInquiry() {
        return "estimate/estimateInquiry";
    }

    @GetMapping("/inquiry/article")
    public String estimateInquiryArticle() {
        return "estimate/estimateInquiryArticle";
    }

    @GetMapping("/reply")
    public String estimateReply() {
        return "estimate/estimateReply";
    }

    @GetMapping("/reply/article")
    public String estimateReplyArticle() {
        return "estimate/estimateReplyArticle";
    }

}

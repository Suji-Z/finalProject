package com.project.tour.controller;

import com.project.tour.domain.EstimateInquiry;
import com.project.tour.domain.EstimateInquiryForm;
import com.project.tour.service.EstimateInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/estimate")
public class EstimateController {

    private final EstimateInquiryService estimateInquiryService;

    @GetMapping("/list")
    public String estimateList(Model model, @PageableDefault Pageable pageable) {

        Page<EstimateInquiry> paging = estimateInquiryService.getList(pageable);

        model.addAttribute("paging",paging);

        return "estimate/estimateList";
    }

    @GetMapping("/upload")
    public String estimateInquiryget(Model model) {
        model.addAttribute("estimateInquiryForm", new EstimateInquiryForm());
        return "estimate/estimateInquiry";
    }


    @PostMapping("/upload")
    public String estimateInquirypost(EstimateInquiryForm estimateInquiryForm) {

        estimateInquiryService.create(estimateInquiryForm);

        return "redirect:/estimate/list";
    }

    @GetMapping("/inquiry/article/{id}")
    public String estimateInquiryArticleget(@PathVariable("id") Integer id,EstimateInquiryForm estimateInquiryForm) {




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

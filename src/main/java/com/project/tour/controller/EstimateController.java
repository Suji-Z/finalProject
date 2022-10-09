package com.project.tour.controller;

import com.project.tour.domain.EstimateInquiry;
import com.project.tour.domain.EstimateInquiryForm;
import com.project.tour.service.EstimateInquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

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

    @GetMapping("/inquiry/upload")
    public String estimateInquiryUpload(Model model) {
        model.addAttribute("estimateInquiryForm", new EstimateInquiryForm());
        return "estimate/estimateInquiry";
    }

    @PostMapping("/inquiry/upload")
    public String estimateInquiryUpload(EstimateInquiryForm estimateInquiryForm) {

        estimateInquiryService.create(estimateInquiryForm);

        return "redirect:/estimate/list";
    }

    @GetMapping("/inquiry/article/{id}")
    public String estimateInquiryArticle(@PathVariable("id") Long id,EstimateInquiryForm estimateInquiryForm,Model model) {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        model.addAttribute("inquiry",inquiry);

        return "estimate/estimateInquiryArticle";
    }

    @GetMapping("/inquiry/delete/{id}")
    public String estimateInquiryDelete(@PathVariable("id") Long id,Principal principal,EstimateInquiryForm inquiryForm) {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

//        if(!inquiry.getEmail().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
//        }

        estimateInquiryService.delete(inquiry);

        return "redirect:/estimate/list";
    }

    @GetMapping("/inquiry/modify/{id}")
    public String estimateInquiryModify(@PathVariable("id") Long id,Principal principal,EstimateInquiryForm inquiryForm) {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        inquiryForm.setTitle(inquiry.getTitle());
        inquiryForm.setLocation(inquiry.getLocation());
        inquiryForm.setACount(inquiry.getACount());
        inquiryForm.setBCount(inquiry.getBCount());
        inquiryForm.setCCount(inquiry.getCCount());
        inquiryForm.setPrice(inquiry.getPrice());
        inquiryForm.setContent(inquiry.getContent());
        inquiryForm.setFlexibleDay(inquiry.getFlexibleDay());

        return "estimate/estimateInquiry";
    }

    @PostMapping("/inquiry/modify/{id}")
    public String estimateInquiryModify(EstimateInquiryForm inquiryForm, BindingResult bindResult,@PathVariable("id") Long id, Principal principal) {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        estimateInquiryService.modify(inquiry,inquiryForm);

        return String.format("redirect:/estimate/inquiry/article/%s", id);
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

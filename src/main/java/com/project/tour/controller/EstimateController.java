package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.service.EstimateInquiryService;
import com.project.tour.service.EstimateReplyService;
import com.project.tour.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/estimate")
public class EstimateController {


    private final EstimateInquiryService estimateInquiryService;
    private final EstimateReplyService estimateReplyService;
    private final MemberService memberService;

    /** 견적문의 리스트 출력 */
    @GetMapping("/list")
    public String estimateList(Model model, @PageableDefault Pageable pageable) {

        Page<EstimateInquiry> paging = estimateInquiryService.getList(pageable);

        model.addAttribute("paging",paging);

        return "estimate/estimateList";
    }

    /** 견적문의 업로드 */
    @PreAuthorize("isAuthenticated()") //로그인해야지 접근가능
    @GetMapping("/inquiry/upload")
    public String estimateInquiryUpload(Model model,Principal principal) {
        model.addAttribute("estimateInquiryForm", new EstimateInquiryForm());
        return "estimate/estimateInquiry";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/inquiry/upload")
    public String estimateInquiryUpload(@Validated EstimateInquiryForm estimateInquiryForm,BindingResult bindingResult,Principal principal) {

        /* 검증에 실패하면 다시 입력폼으로 */
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "estimate/estimateInquiry";
        }

        Member member= memberService.getMember(principal.getName());

        estimateInquiryService.create(estimateInquiryForm,member.getEmail());

        return "redirect:/estimate/list";
    }

    /** 견적문의 게시글이동 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/inquiry/article/{id}")
    public String estimateInquiryArticle(EstimateInquiryForm estimateInquiryForm,@PathVariable("id") Long id,Model model,Principal principal) {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        model.addAttribute("inquiry",inquiry);

        return "estimate/estimateInquiryArticle";
    }

    /** 견적문의 삭제 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/inquiry/delete/{id}")
    public String estimateInquiryDelete(@PathVariable("id") Long id,Principal principal) {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

//        if(!inquiry.getEmail().equals(principal.getName())) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
//        }

        estimateInquiryService.delete(inquiry);

        return "redirect:/estimate/list";
    }

    /** 견적문의 수정 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/inquiry/modify/{id}")
    public String estimateInquiryModify(@PathVariable("id") Long id,Principal principal,EstimateInquiryForm inquiryForm) {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        inquiryForm.setTitle(inquiry.getTitle());
        inquiryForm.setLocation1(inquiry.getLocation1());
        inquiryForm.setLocation2(inquiry.getLocation2());
        inquiryForm.setACount(inquiry.getACount());
        inquiryForm.setBCount(inquiry.getBCount());
        inquiryForm.setCCount(inquiry.getCCount());
        inquiryForm.setStartDay(inquiry.getStartDay());
        inquiryForm.setEndDay(inquiry.getEndDay());
        inquiryForm.setPrice(inquiry.getPrice());
        inquiryForm.setContent(inquiry.getContent());
        inquiryForm.setFlexibleDay(inquiry.getFlexibleDay());

        return "estimate/estimateInquiry";
    }

    @PostMapping("/inquiry/modify/{id}")
    public String estimateInquiryModify(@Validated EstimateInquiryForm inquiryForm, BindingResult bindingResult,@PathVariable("id") Long id, Principal principal) {

        /* 검증에 실패하면 다시 입력폼으로 */
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "estimate/estimateInquiry";
        }

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        estimateInquiryService.modify(inquiry,inquiryForm);

        return String.format("redirect:/estimate/inquiry/article/%s", id);
    }

    /** 답변달기 */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reply/{id}")
    public String estimateReply(@PathVariable("id") Long id,Principal principal,Model model) {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        /** 견적 범위 내의 패키지리스트 */
        List<Package> recomPackages = estimateReplyService.getPackages(inquiry);

        model.addAttribute("estimateReplyForm", new EstimateReplyForm());
        model.addAttribute("inquiry",inquiry);
        model.addAttribute("packages",recomPackages);
        model.addAttribute("id",id);

        return "estimate/estimateReply";
    }

    @PostMapping("/reply/{id}")
    public String estimateReply(@Validated EstimateReplyForm replyForm, BindingResult bindingResult,
                                @PathVariable("id") Long id,Principal principal) {

        /* 검증에 실패하면 다시 입력폼으로 */
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "estimate/estimateReply";
        }

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);
        estimateReplyService.create(inquiry,replyForm);

        return "redirect:/estimate/list";
    }

    /** 답변게시글 이동 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/article/{id}")
    public String estimateReplyArticle(@PathVariable("id") Long id,Principal principal,Model model) {

        EstimateReply reply = estimateReplyService.getArticle(id);

        String[] packages = reply.getRecomPackage().split(",");

        List<Package> recomPackages = estimateReplyService.recom(packages);

        model.addAttribute("reply",reply);
        model.addAttribute("recomPackages",recomPackages);

        return "estimate/estimateReplyArticle";
    }
    /** 답변 수정 */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reply/modify/{id}")
    public String estimateReplyModifyg(EstimateReplyForm replyForm, @PathVariable("id") Long id,Principal principal,Model model) {

        EstimateReply reply = estimateReplyService.getArticle(id);

        replyForm.setTitle(reply.getTitle());
        replyForm.setContent(reply.getContent());
        replyForm.setCreated(LocalDateTime.now());

        EstimateInquiry inquiry = reply.getEstimateInquiry();

        List<Package> recomPackages = estimateReplyService.getPackages(inquiry);

        model.addAttribute("packages",recomPackages);
        model.addAttribute("inquiry",inquiry);

        return "estimate/estimateReply";
    }

    @PostMapping("/reply/modify/{id}")
    public String estimateReplyModifyp(@Validated EstimateReplyForm replyForm,BindingResult bindingResult,@PathVariable("id") Long id,Principal principal,Model model) {

        EstimateReply reply = estimateReplyService.getArticle(id);

        /* 검증에 실패하면 다시 입력폼으로 */
        if(bindingResult.hasErrors()){

            EstimateInquiry inquiry = reply.getEstimateInquiry();

            model.addAttribute("inquiry",inquiry);

            log.info("errors = {}",bindingResult);
            return "estimate/estimateReply";
        }

        estimateReplyService.modify(reply,replyForm);


        return String.format("redirect:/estimate/reply/article/%s", id);
    }

    /** 답변 삭제 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/delete/{id}")
    public String estimateReplyDelete(@PathVariable("id") Long id,Principal principal) {

        EstimateReply reply = estimateReplyService.getArticle(id);

        estimateReplyService.delete(reply);

        return "redirect:/estimate/list";
    }


}

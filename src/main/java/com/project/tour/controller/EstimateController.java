package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.EstimateInquiryService;
import com.project.tour.service.EstimateReplyService;
import com.project.tour.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.text.ParseException;
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

    /** 마이 견적문의 리스트 출력 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mylist")
    public String estimateMyList(Model model, @PageableDefault Pageable pageable,Principal principal,@LoginUser SessionUser user) {


        Member member = null;
        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else {
            member = memberService.getName(user.getEmail());
        }

        Page<EstimateInquiry> paging = estimateInquiryService.getMyList(member,pageable);

        model.addAttribute("paging",paging);
        model.addAttribute("member",member);
        return "estimate/estimateList";
    }

    /** 견적문의 업로드 */
    @PreAuthorize("isAuthenticated()") //로그인해야지 접근가능
    @GetMapping("/inquiry/upload")
    public String estimateInquiryUpload(Model model) {
        model.addAttribute("estimateInquiryForm", new EstimateInquiryForm());
        return "estimate/estimateInquiry";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/inquiry/upload")
    public String estimateInquiryUpload(@Validated EstimateInquiryForm estimateInquiryForm, BindingResult bindingResult, Principal principal,
                                        @LoginUser SessionUser user) {

        /* 검증에 실패하면 다시 입력폼으로 */
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "estimate/estimateInquiry";
        }

        Member member = null;
        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else {
            member = memberService.getName(user.getEmail());
        }

        estimateInquiryService.create(estimateInquiryForm,member);

        return "redirect:/estimate/list";
    }

    /** 견적문의 게시글이동 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/inquiry/article/{id}")
    public String estimateInquiryArticle(@RequestParam("page") String page,@PathVariable("id") Long id,Model model,@LoginUser SessionUser user,Principal principal) {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        /** 한게시글에 답변이 두개이상 달리면 작동할 수 없는 코드임
         * 나중에 두개이상 답변달았을때 어떤 형식으로 할지 고민중이기 때문에
         * 일단 이렇게 해두고 나중에 고치는걸로 */
        EstimateReply reply = estimateReplyService.getArticle(inquiry);

        String email;
        String name;
        if (memberService.existByEmail(principal.getName())) {
            Member member = memberService.getName(principal.getName());
            email = member.getEmail();
            name = member.getName();
        } else {
            email = user.getEmail();
            name = user.getName();
        }

        model.addAttribute("inquiry",inquiry);
        model.addAttribute("page",page);
        model.addAttribute("email", email);
        model.addAttribute("name", name);
        model.addAttribute("reply", reply);

        return "estimate/estimateInquiryArticle";
    }

    /** 견적문의 삭제 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/inquiry/delete/{id}")
    public String estimateInquiryDelete(@RequestParam("page") String page,@PathVariable("id") Long id,@LoginUser SessionUser user,Principal principal) {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        Member member;
        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else {
            member = memberService.getName(user.getEmail());
        }

       if(!inquiry.getMember().getEmail().equals(member.getEmail())) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
        }

        estimateInquiryService.delete(inquiry);

        return "redirect:/estimate/list?page="+page;
    }

    /** 견적문의 수정 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/inquiry/modify/{id}")
    public String estimateInquiryModify(@RequestParam("page") String page,@PathVariable("id") Long id,EstimateInquiryForm inquiryForm) {

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
    public String estimateInquiryModify(@RequestParam("page") String page,@Validated EstimateInquiryForm inquiryForm, BindingResult bindingResult,@PathVariable("id") Long id) {

        /* 검증에 실패하면 다시 입력폼으로 */
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "estimate/estimateInquiry";
        }

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        estimateInquiryService.modify(inquiry,inquiryForm);

        return String.format("redirect:/estimate/inquiry/article/%s?page=%s", id,page);
    }

    /** 답변달기 */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reply/{id}")
    public String estimateReply(@PathVariable("id") Long id,Model model) throws ParseException {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);

        /** 견적 범위 내의 패키지리스트 */
        List<EstimateSearchDTO> recomPackages = estimateReplyService.getPackages(inquiry);

        model.addAttribute("estimateReplyForm", new EstimateReplyForm());
        model.addAttribute("inquiry",inquiry);
        model.addAttribute("packages",recomPackages);
        model.addAttribute("id",id);

        return "estimate/estimateReply";
    }

    @PostMapping("/reply/{id}")
    public String estimateReply(@Validated EstimateReplyForm replyForm, BindingResult bindingResult,
                                @PathVariable("id") Long id,Model model) throws ParseException {

        EstimateInquiry inquiry = estimateInquiryService.getArticle(id);
        List<EstimateSearchDTO> recomPackages = estimateReplyService.getPackages(inquiry);

        /* 검증에 실패하면 다시 입력폼으로 */
        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            model.addAttribute("inquiry",inquiry);
            model.addAttribute("packages",recomPackages);
            return "estimate/estimateReply";
        }

        estimateReplyService.create(inquiry,replyForm);

        return "redirect:/estimate/list";
    }

    /** 답변게시글 이동 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/reply/article/{id}")
    public String estimateReplyArticle(@PathVariable("id") Long id,Model model,@LoginUser SessionUser user,Principal principal) {

        EstimateReply reply = estimateReplyService.getArticle(id);

        //추천패키지 
        String[] packages;
        List<Package> recomPackages = null;
        if(reply.getRecomPackage()!=null) {
            packages = reply.getRecomPackage().split(",");
            recomPackages = estimateReplyService.recom(packages);
        }

        //관리자,글쓴이 외 비밀글처리
        Member member;
        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else {
            member = memberService.getName(user.getEmail());
        }
        model.addAttribute("reply",reply);
        model.addAttribute("recomPackages",recomPackages);
        model.addAttribute("member",member);


        return "estimate/estimateReplyArticle";
    }
    /** 답변 수정 */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/reply/modify/{id}")
    public String estimateReplyModifyg(EstimateReplyForm replyForm, @PathVariable("id") Long id,Model model) throws ParseException {

        EstimateReply reply = estimateReplyService.getArticle(id);

        replyForm.setTitle(reply.getTitle());
        replyForm.setContent(reply.getContent());
        replyForm.setCreated(LocalDateTime.now());
        replyForm.setRecomPackage(reply.getRecomPackage());

        log.info(replyForm.getRecomPackage());

        EstimateInquiry inquiry = reply.getEstimateInquiry();

        List<EstimateSearchDTO> recomPackages = estimateReplyService.getPackages(inquiry);

        model.addAttribute("packages",recomPackages);
        model.addAttribute("inquiry",inquiry);

        return "estimate/estimateReply";
    }

    @PostMapping("/reply/modify/{id}")
    public String estimateReplyModifyp(@Validated EstimateReplyForm replyForm,BindingResult bindingResult,@PathVariable("id") Long id,Model model) {

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
    public String estimateReplyDelete(@PathVariable("id") Long id) {

        EstimateReply reply = estimateReplyService.getArticle(id);

        estimateReplyService.delete(reply);

        return "redirect:/estimate/list";
    }


}

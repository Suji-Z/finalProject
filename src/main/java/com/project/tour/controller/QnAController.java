package com.project.tour.controller;


import com.project.tour.domain.*;
import com.project.tour.service.MemberService;
import com.project.tour.service.QnAReplyService;
import com.project.tour.service.QnAService;
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

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/qna")
public class QnAController {

    private final QnAService qnAService;

    private final MemberService memberService;

    private final QnAReplyService qnAReplyService;

    //Q&A list
    @GetMapping("/list")
    public String questionList(Model model, @PageableDefault Pageable pageable) {

        Page<QnA> paging = qnAService.getList(pageable);

        model.addAttribute("paging",paging);

        return "qna/qnaList";
    }


    //Quesiton 작성
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/question/write")
    public String question(Model model, Principal principal){

        model.addAttribute("QnAForm", new QnAForm());
        return "qna/questionCreate";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/question/write")
    public String question(@Validated QnAForm qnAForm, BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "qna/qnaList";
        }

        Member member = memberService.getMember(principal.getName());
        qnAService.create(qnAForm,member);

        return "redirect:/qna/list";

    }

    //Question Article

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/article/{id}")
    public String questionArticle(QnAForm qnAForm, @PathVariable("id") Long id, Model model, Principal principal) {

        QnA qna = qnAService.getquestion(id);

        model.addAttribute("qna",qna);

        return "qna/questionArticle";
    }

    //질문 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/modify/{id}")
    public String questionModify(@PathVariable("id") Long id,Principal principal,QnAForm qnAForm,Model model) {

        QnA qnA = qnAService.getquestion(id);

        qnAForm.setSubject(qnA.getSubject());
        qnAForm.setQnacategory(qnA.getQnacategory());
        qnAForm.setContent(qnA.getContent());
        qnAForm.setPassword(qnA.getPassword());

        model.addAttribute("QnAForm",qnAForm);

        return "qna/questionCreate";
    }

    @PostMapping("/question/modify/{id}")
    public String questionModify(@Validated QnAForm qnAForm, BindingResult bindingResult,@PathVariable("id") Long id, Principal principal) {

        if(bindingResult.hasErrors()){
            log.info("errors = {}",bindingResult);
            return "redirect:/qna/list";
        }

        QnA qnA = qnAService.getquestion(id);

        qnAService.modify(qnA,qnAForm);

        return String.format("redirect:/qna/question/article/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/delete/{id}")
    public String questionDelete(@PathVariable("id") Long id,Principal principal) {

        QnA qnA = qnAService.getquestion(id);

       qnAService.delete(qnA);

        return "redirect:/qna/list";
    }

}

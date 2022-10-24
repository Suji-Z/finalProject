package com.project.tour.controller;


import com.project.tour.domain.*;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.QnAReplyService;
import com.project.tour.service.QnAService;
import com.sun.org.apache.xpath.internal.operations.Mod;
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

        model.addAttribute("paging", paging);

        return "qna/qnaList";
    }


    //Quesiton 작성
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/question/write")
    public String question(Model model, Principal principal) {

        model.addAttribute("QnAForm", new QnAForm());
        return "qna/questionCreate";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/question/write")
    public String question(@Validated QnAForm qnAForm, BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "qna/qnaList";
        }

        Member member = memberService.getMember(principal.getName());
        qnAService.create(qnAForm, member);

        return "redirect:/qna/list";

    }

    //Question Article

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/article/{id}")
    public String questionArticle(QnAForm qnAForm, @LoginUser SessionUser user, @PathVariable("id") Long id, Model model, Principal principal) {

        QnA qna = qnAService.getquestion(id);

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

        model.addAttribute("qna", qna);
        model.addAttribute("email", email);
        model.addAttribute("name", name);

        return "qna/questionArticle";
    }

    //질문 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/modify/{id}")
    public String questionModify(@PathVariable("id") Long id, Principal principal, QnAForm qnAForm, Model model) {

        QnA qnA = qnAService.getquestion(id);

        qnAForm.setSubject(qnA.getSubject());
        qnAForm.setQnacategory(qnA.getQnacategory());
        qnAForm.setContent(qnA.getContent());

        model.addAttribute("QnAForm", qnAForm);

        return "qna/questionCreate";
    }

    @PostMapping("/question/modify/{id}")
    public String questionModify(@Validated QnAForm qnAForm, BindingResult bindingResult, @PathVariable("id") Long id, Principal principal) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "redirect:/qna/list";
        }

        QnA qnA = qnAService.getquestion(id);

        qnAService.modify(qnA, qnAForm);

        return String.format("redirect:/qna/question/article/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/question/delete/{id}")
    public String questionDelete(@PathVariable("id") Long id, Principal principal) {

        QnA qnA = qnAService.getquestion(id);

        qnAService.delete(qnA);

        return "redirect:/qna/list";
    }

    //reply 게시글 작성

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/answer/write/{id}")
    public String replyCreate(@PathVariable("id") Long id, Principal principal, QnAReplyForm qnAReplyForm, Model model) {

        QnA qna = qnAService.getquestion(id);
        model.addAttribute("qna", qna);

        model.addAttribute("QnAReplyForm", new QnAReplyForm());
        model.addAttribute("name", principal.getName());


        return "qna/answerCreate";

    }

    @PostMapping("/answer/write/{id}")
    public String replyCreate(Model model, @PathVariable("id") Long id, @Valid QnAReplyForm qnAReplyForm,
                              BindingResult bindingResult, Principal principal) {
        QnA qna = qnAService.getquestion(id);

        if (bindingResult.hasErrors()) {
            //입력실패시, 입력폼으로 리턴
            return "qna/answerCreate";
        }

        qnAReplyService.write(qna, qnAReplyForm);

        return "redirect:/qna/list";
    }

    @GetMapping("/answer/article/{id}")
    public String qnaReplyArticle(@PathVariable("id") Long id, Model model) {
        QnA_Reply reply = qnAReplyService.getReply(id);
        QnA qna = qnAService.getquestion(id);

        model.addAttribute("qna", qna);
        model.addAttribute("reply", reply);

        return "qna/answerArticle";

    }

    // 답글 수정
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/answer/modify/{id}")
    public String modifyReply(QnAReplyForm qnAReplyForm, @PathVariable("id") Long id,
                              Model model) {

        QnA_Reply reply = qnAReplyService.getReply(id);

        qnAReplyForm.setTitle(reply.getTitle());
        qnAReplyForm.setContent(reply.getContent());

        model.addAttribute("QnAReplyForm", qnAReplyForm);


        return "qna/answerCreate";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/answer/modify/{id}")
    public String modifyReply(@Valid QnAReplyForm qnAReplyForm, BindingResult bindingResult,
                              @PathVariable("id") Long id, Principal principal) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "redirect:/qna/list";
        }

        QnA_Reply reply = qnAReplyService.getReply(id);

        qnAReplyService.modify(reply, qnAReplyForm);


        return String.format("redirect:/qna/answer/article/%s", id);
    }

    @GetMapping("/answer/delete/{id}")
    public String deleteReply(@PathVariable("id") Long id, Principal principal) {

        QnA_Reply reply = qnAReplyService.getReply(id);

        qnAReplyService.delete(reply);

        return "redirect:/qna/list";

    }
}
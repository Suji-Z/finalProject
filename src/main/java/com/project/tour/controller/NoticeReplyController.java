package com.project.tour.controller;

import com.project.tour.domain.Member;
import com.project.tour.domain.Notice;
import com.project.tour.domain.NoticeReply;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.NoticeReplyService;
import com.project.tour.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/noticeReply")
public class NoticeReplyController {

    private final MemberService memberService;
    private final NoticeReplyService noticeReplyService;
    private final NoticeService noticeService;

    //댓글 달기
    @PostMapping("/write")
    @ResponseBody
    public String noticeReplyCreate(@RequestParam("replyForm") HashMap<String,Object> replyForm,
                                                    Principal principal, @LoginUser SessionUser user, Model model){

        System.out.println("replyForm = " + replyForm);
        System.out.println("replyForm = " + replyForm.get("noticeNum"));

        //로그인 확인
        Member member;
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        noticeReplyService.create(replyForm);
        model.addAttribute("commentList", noticeReplyService.getList((Notice) replyForm.get("notice")));

        // 수정&삭제 버튼 게시를 위한 유저 정보 전달
        Map<String, Object> userInform = new HashMap<String, Object>();
        userInform.put("member", member);
        model.addAttribute("userInform", userInform);


        return "/write :: #commentTable";





    }

    //댓글 수정

    //댓글 삭제

    //댓글 좋아요
}

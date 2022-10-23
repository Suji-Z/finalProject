package com.project.tour.controller;

import com.project.tour.domain.Member;
import com.project.tour.domain.Notice;
import com.project.tour.domain.NoticeReply;
import com.project.tour.domain.NoticeReplyForm;
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
    public String noticeReplyCreate(@RequestBody Map<String,Object> replyForm,
                                    Principal principal, @LoginUser SessionUser user,
                                    Model model){

        System.out.println("요기");

        //로그인 확인
        Member member;
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        Long memberNum = Long.parseLong(replyForm.get("memberNum").toString());
        Long noticeNum = Long.parseLong(replyForm.get("noticeNum").toString());

        noticeReplyService.create(replyForm, memberNum, noticeNum);
        model.addAttribute("commentList", noticeReplyService.getList(noticeNum));

        System.out.println("여기");

        // 수정&삭제 버튼 게시를 위한 유저 정보 전달
        Map<String, Object> userInform = new HashMap<String, Object>();
        userInform.put("member", member);
        model.addAttribute("userInform", userInform);

        System.out.println("저기");

        return "notice/notice_article :: #commentTable";





    }

    //댓글 수정

    //댓글 삭제

    //댓글 좋아요
}

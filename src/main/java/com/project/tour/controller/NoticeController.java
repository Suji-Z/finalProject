package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.NoticeReplyService;
import com.project.tour.service.NoticeService;
import com.project.tour.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private final NoticeService noticeService;
    private final MemberService memberService;
    private final NoticeReplyService noticeReplyService;

    //글작성 페이지 띄우기 : 관리자만 가능
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/write")
    public String noticeWrite(@LoginUser SessionUser user, Principal principal,
                              NoticeForm noticeForm,Model model) {

    int pinCount = noticeService.searchPin();
    model.addAttribute("pinCount",pinCount);

        return "notice/notice_create";
    }

    //글작성 데이터 저장하기 : 관리자만 가능
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/write")
    public String noticeWrite2(@Valid NoticeForm noticeForm, BindingResult bindingResult,
                               @RequestParam("fileName") MultipartFile multipartFile) throws IOException {

        if(bindingResult.hasErrors()){
            return "notice/list";
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        noticeForm.setNoticeImage(fileName);

        Notice notice = noticeService.create(noticeForm); //저장 후 객체 호출

        String uploadDir =  "review-photo/" + notice.getId();

        if(!fileName.isEmpty()) {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        }

        return"redirect:/notice/list";

    }

    //공지사항 리스트 출력 : 로그인안해도 볼 수 있음, 글 클릭은 로그인 한 사람만
    @GetMapping("/list")
    public String noticeList(Model model, @PageableDefault Pageable pageable, Principal principal,
                              @LoginUser SessionUser user) {

        Page<Notice> paging = noticeService.getList(pageable);
        model.addAttribute("paging",paging);

        return "notice/notice_list";

    }

    //게시글 보기
    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/article/{id}")
    public String noticeArticle(@PathVariable("id") Long id, Model model, Principal principal, @LoginUser SessionUser user) {

        String beforeSubject = noticeService.getSubject(id-1);
        String afterSubject = noticeService.getSubject(id+1);
        Notice notice = noticeService.getNotice(id);

        model.addAttribute("commentList", noticeReplyService.getList(id));


        //로그인 확인
        Member member;
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        //hitCount 올리기
        int hitCount = notice.getHitCount() + 1;
        noticeService.updateHitCount(hitCount,id);

        //recommendStatus 찾기
        int recommendStatus = noticeService.searchRecommend(member, id);

        if(recommendStatus==1){
            model.addAttribute("url","/assets/img/icon/hand-thumbs-up-fill.svg");
        } else{
            model.addAttribute("url","/assets/img/icon/hand-thumbs-up.svg");
        }

        model.addAttribute("notice",notice);
        model.addAttribute("beforeSubject", beforeSubject);
        model.addAttribute("afterSubject",afterSubject);
        model.addAttribute("recommendStatus", recommendStatus);
        model.addAttribute("member",member);

        return "notice/notice_article";
    }

    //게시글 수정 : 관리자만 가능
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/modify/{id}")
    public String noticeModify(@PathVariable("id") Long id, Model model, NoticeForm noticeForm){

        Notice notice = noticeService.getNotice(id);

        noticeForm.setSubject(notice.getSubject());
        noticeForm.setCategory(notice.getCategory());
        noticeForm.setContent(notice.getContent());

        model.addAttribute("notice", notice);
        model.addAttribute("status","modify");

        return "notice/notice_create";

    }

    //게시글 수정
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/modify/{id}")
    public String noticeModify2(@PathVariable("id") Long id, Model model, @Valid NoticeForm noticeForm,
                                BindingResult bindingResult, @RequestParam("fileName") MultipartFile multipartFile) throws IOException{

        if(bindingResult.hasErrors()){
            return "review/review_write";
        }

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        noticeForm.setNoticeImage(fileName);

        noticeService.updateNotice(noticeForm,id);

        String uploadDir =  "notice-photo/notice" + id + "'s file";

        if(!fileName.isEmpty()){
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
        }

        return String.format("redirect:/notice/article/%s",id);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/delete/{id}")
    public String noticeDelete(@PathVariable("id") Long id) {

        noticeService.deleteNotice(id);

        return "redirect:/notice/list";

    }

    // 추천해요 버튼
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/recommend")
    public @ResponseBody HashMap<String,Object> noticeRecommend(@RequestParam("recommendStatus") int recommendStatus,
                                                                Principal principal, @LoginUser SessionUser user,
                                                                @RequestParam("id") Long id) {

        Member member;
        if(memberService.existByEmail(principal.getName())){
            member = memberService.getName(principal.getName());
        }else{
            member = memberService.getName(user.getEmail());
        }

        if(recommendStatus==1){
            noticeService.createRecommend(member, id);
        }else if(recommendStatus==0){
            noticeService.deleteRecommend(member, id);
        }

        HashMap<String,Object> recommendInfo = new HashMap<>();
        recommendInfo.put("recommendStatus", recommendStatus);

        return recommendInfo;
    }


    //카테고리 필터 씌우기
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/category")
    public String categoryFilter(@RequestParam("category") String category, @PageableDefault Pageable pageable,
                                 Model model) {

        Page<Notice> paging = noticeService.getListByCategory(category, pageable);
        model.addAttribute("paging",paging);

        return "notice/notice_list :: #commentTable";
    }

    //검색하기
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/search")
    public String search(@RequestParam("category") String category, @RequestParam("searchKeyword") String searchKeyword,
                         @PageableDefault Pageable pageable, Model model) {

        Page<Notice> paging = noticeService.getListBySearch(category, searchKeyword, pageable);
        model.addAttribute("paging",paging);

        return "notice/notice_list :: #commentTable";
    }





}

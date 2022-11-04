package com.project.tour.controller;



import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.PackageService;
import com.project.tour.service.ReviewReplyService;
import com.project.tour.service.ReviewService;
import com.project.tour.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;

    private final ReviewReplyService reviewReplyService;

    private final PackageService packageService;



    //모든 리뷰 보기
    @RequestMapping
    public String allReviewList(Model model, @PageableDefault(size = 5) Pageable pageable,Principal principal,@LoginUser SessionUser user ){


        Page<Review> paging = reviewService.getList(pageable);
        model.addAttribute("paging",paging);

        return "review/review_All";

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/write")
    public String write1(Model model, ReviewForm reviewForm,Principal principal,@LoginUser SessionUser user){

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }


        //결제완료된 부킹리스트 가져오기
        Long memberId = member.getId();
        int status = 2; // 0:예약확인중 1:결제대기중 2:결제완료

        List<UserBooking> bookingReview =  reviewService.getBookingReview(memberId,status);

        System.out.println(bookingReview.size());

        model.addAttribute("bookingReview",bookingReview);

        return "review/review_write";

    }

    @PreAuthorize("isAuthenticated()") //로그인 해야지만 작성 가능
    @PostMapping(value = "/write")
    public String write2(@Valid ReviewForm reviewForm, BindingResult bindingResult,Principal principal,@LoginUser SessionUser user,Model model,
            @RequestParam("image") MultipartFile multipartFile, @RequestParam("packageNum") Long packageNum) throws IOException{

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        if (bindingResult.hasErrors()){
            Long memberId = member.getId();
            int status = 2; // 0:예약확인중 1:결제대기중 2:결제완료

            List<UserBooking> bookingReview =  reviewService.getBookingReview(memberId,status);

            System.out.println(bookingReview.size());

            model.addAttribute("bookingReview",bookingReview);
            return "review/review_write";
        }



        Package reviewPackage = packageService.getPackage(packageNum);



        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        reviewForm.setReviewImage(fileName);

        Review review = reviewService.create(reviewForm.getSubject(),reviewForm.getContent(),
                reviewForm.getReviewImage(), reviewForm.getScore(),member,reviewPackage);

        String uploadDir =  "review-photo/" + review.getId();

        FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);

        return "redirect:/review";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/update/{id}")
    public String update1(Model model,ReviewForm reviewForm, @PathVariable("id") Long id, Principal principal,@LoginUser SessionUser user){

        Review review = reviewService.getReview(id);

        reviewForm.setSubject(review.getSubject());
        reviewForm.setContent(review.getContent());
        reviewForm.setScore(review.getScore());

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        //결제완료된 부킹리스트 가져오기
        Long memberId = member.getId();
        int status = 2; // 0:예약확인중 1:결제대기중 2:결제완료

        List<UserBooking> bookingReview =  reviewService.getBookingReview(memberId,status);

        System.out.println(bookingReview.size());

        model.addAttribute("bookingReview",bookingReview);

        model.addAttribute("review", review);

        return "review/review_write";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/update/{id}")
    public String update2(@Valid ReviewForm reviewform, BindingResult bindingResult,
                          @PathVariable("id") Long id,@RequestParam("image") MultipartFile multipartFile,Principal principal,
                          @RequestParam("packageNum") Long packageNum)  throws IOException {

        if(bindingResult.hasErrors()){
            return "review/review_write";
        }

        Review review = reviewService.getReview(id);


        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        reviewform.setReviewImage(fileName);

        Package reviewPackage = packageService.getPackage(packageNum);

        reviewService.update(review,reviewform.getSubject(),reviewform.getContent(),
                reviewform.getReviewImage(),reviewform.getScore(),reviewPackage);

        String uploadDir =  "review-photo/" + review.getId();

        FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);

        return String.format("redirect:/review/article/%s",id);

    }


    @RequestMapping (value = "/article/{id}")
    public String article(Model model, @PathVariable("id") Long id, ReviewReplyForm reviewReplyForm,
                          Principal principal,@LoginUser SessionUser user){

        Review review = reviewService.getReview(id);

        Member member;

        if(principal==null && user==null){ //로그아웃

            System.out.println("로그아웃이다");
            model.addAttribute("url","/assets/img/icon/ReviewHeart1.png");
            model.addAttribute("recommendStatus", 0);
            model.addAttribute("loginStatus1","n");
            model.addAttribute("member","n");


        }else if(user!=null){ //간편로그인
            System.out.println("간편로그인이다");

            member = memberService.getName(user.getEmail());

            Long id2 = member.getId();

            int reviewRecommend = reviewService.getReviewLike(id2,id);

            if(reviewRecommend==1){
                model.addAttribute("url","/assets/img/icon/ReviewHeart2.png");
                model.addAttribute("recommendStatus", 1);
                model.addAttribute("loginStatus1","y");
                model.addAttribute("member",member);


            }else{
                model.addAttribute("url","/assets/img/icon/ReviewHeart1.png");
                model.addAttribute("recommendStatus", 0);
                model.addAttribute("loginStatus1","y");
                model.addAttribute("member",member);
            }

        }else if (principal!=null){ //일반회원
            System.out.println("일반회원이다");

            member = memberService.getName(principal.getName());

            Long id2 = member.getId();

            int reviewRecommend = reviewService.getReviewLike(id2,id);

            if(reviewRecommend==1){
                model.addAttribute("url","/assets/img/icon/ReviewHeart2.png");
                model.addAttribute("recommendStatus", 1);
                model.addAttribute("loginStatus1","y");
                model.addAttribute("member",member);

            }else{
                model.addAttribute("url","/assets/img/icon/ReviewHeart1.png");
                model.addAttribute("recommendStatus", 0);
                model.addAttribute("loginStatus1","y");
                model.addAttribute("member",member);
            }

        }

        int hitCount = review.getHitCount()+1;
        reviewService.updateHitCount(hitCount,id);


        //model.addAttribute("reviewLike",reviewService.getReviewLike(id2,id));

        model.addAttribute("review",review);


        return "review/review_article";

    }

    @RequestMapping (value = "/abroadList")
    public String abroadList(Model model, @PageableDefault(size = 5) Pageable pageable,Principal principal,@LoginUser SessionUser user ){


        List<String> location = new ArrayList<>();
        location.add("아시아");
        location.add("유럽");
        location.add("미국");

        System.out.println(location);

        Page<Review> paging = reviewService.getAbroadList(location,pageable);
        model.addAttribute("paging",paging);

        return "review/review_Abroad_List";

    }

    @RequestMapping (value = "/jejuList")
    public String jejuList(Model model, @PageableDefault(size = 5) Pageable pageable){

        Page<Review> paging = reviewService.getJejuList("제주",pageable);
        model.addAttribute("paging",paging);

        return "review/review_Jeju_List";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/delete/{id}")
    public String delete(Principal principal, @PathVariable("id") Long id){

        Review review = reviewService.getReview(id);



        reviewService.delete(review);

        return "redirect:/review/abroadList";

    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote")
    public @ResponseBody HashMap<String,Object> reviewVote(@RequestParam("recommendStatus") int recommendStatus,
            Principal principal, @RequestParam("id") Long id, @LoginUser SessionUser user){

        Review review = reviewService.getReview(id);
        System.out.println(id);

        System.out.println("오나요?");
        System.out.println(recommendStatus);

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        if(recommendStatus==1){
            reviewService.vote(review,member);
        }else{
            reviewService.deleteLike(member.getId(),id);
        }

        HashMap<String,Object> recommendInfo = new HashMap<>();
        recommendInfo.put("recommendStatus", recommendStatus);

        return recommendInfo;

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/reply/{id}")
    public  String writeReply(Model model, @PathVariable("id") Long id, @Valid ReviewReplyForm reviewReplyForm,
                              BindingResult bindingResult, Principal principal,@LoginUser SessionUser user){

        Review review = reviewService.getReview(id);

        Member member;

        if(memberService.existByEmail(principal.getName())){

            member = memberService.getName(principal.getName());

        }else{

            member = memberService.getName(user.getEmail());

        }

        if(bindingResult.hasErrors()){
            model.addAttribute("review",review);
            return "review/review_article"; //실패하면 리뷰디테일 띄우기

        }

        Review_reply review_reply = reviewReplyService.create(review,reviewReplyForm.getContent(),member);

        return String.format("redirect:/review/article/%s#reply_%s",
                review_reply.getReviewNum().getId(),review_reply.getId());

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/replyUpdate/{id}")
    public String updateReply(ReviewReplyForm reviewReplyForm, @PathVariable("id") Long id, Model model,
                              BindingResult bindingResult,Principal principal){

        Review_reply review_reply = reviewReplyService.getReply(id);



        reviewReplyForm.setContent(review_reply.getContent());

        return "review/review_reply_write";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/replyUpdate/{id}")
    public String modifyReply(@Valid ReviewReplyForm reviewReplyForm, BindingResult bindingResult,
                               @PathVariable("id") Long id, Principal principal){

        if(bindingResult.hasErrors()){
            return "review/review_reply_write";
        }

        Review_reply review_reply = reviewReplyService.getReply(id);



        reviewReplyService.update(review_reply,reviewReplyForm.getContent());

        return String.format("redirect:/review/article/%s#reply_%s",
                         review_reply.getReviewNum().getId(),review_reply.getId());

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/replyDelete/{id}")
    public String deleteReply(@PathVariable("id")Long id,Principal principal){
        Review_reply review_reply = reviewReplyService.getReply(id);



        reviewReplyService.delete(review_reply);

        return String.format("redirect:/review/article/%s",review_reply.getReviewNum().getId());

    }

}

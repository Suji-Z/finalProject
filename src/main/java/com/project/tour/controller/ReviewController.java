package com.project.tour.controller;


import com.project.tour.domain.*;
import com.project.tour.service.MemberService;
import com.project.tour.service.ReviewReplyService;
import com.project.tour.service.ReviewService;
import com.project.tour.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;

    private final ReviewReplyService reviewReplyService;

    @GetMapping(value = "/write")
    public String write1(ReviewForm reviewForm){

        return "review/review_write";

    }

    @PostMapping(value = "/write")
    public String write2(@Valid ReviewForm reviewForm, BindingResult bindingResult,
            @RequestParam("image") MultipartFile multipartFile) throws IOException{

        if(bindingResult.hasErrors()){
            return "review/review_write";
        }

       // Member member = memberService.getMember(principal.getName());


        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        reviewForm.setReviewImage(fileName);

        Review review = reviewService.create(reviewForm.getSubject(),reviewForm.getContent(),
                reviewForm.getReviewImage(), reviewForm.getScore());

        String uploadDir =  "review-photo/" + review.getId();

        FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);

        return "redirect:/review/abroadList";

    }

    @GetMapping(value = "/update/{id}")
    public String update1(ReviewForm reviewForm, @PathVariable("id") Long id, Model model){

        Review review = reviewService.getReview(id);

        reviewForm.setSubject(review.getSubject());
        reviewForm.setContent(review.getContent());
        reviewForm.setScore(review.getScore());

        model.addAttribute("review",review);

        return "review/review_write";

    }

    @PostMapping(value = "/update/{id}")
    public String update2(@Valid ReviewForm reviewform, BindingResult bindingResult,
                          @PathVariable("id") Long id,@RequestParam("image") MultipartFile multipartFile)  throws IOException {

        if(bindingResult.hasErrors()){
            return "review/review_write";
        }

        Review review = reviewService.getReview(id);

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        reviewform.setReviewImage(fileName);

        reviewService.update(review,reviewform.getSubject(),reviewform.getContent(),
                reviewform.getReviewImage(),reviewform.getScore());

        String uploadDir =  "review-photo/" + review.getId();

        FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);

        return String.format("redirect:/review/article/%s",id);

    }

    @GetMapping (value = "/article/{id}")
    public String article(Model model, @PathVariable("id") Long id){

        Review review = reviewService.getReview(id);
        model.addAttribute("review",review);

        return "review/review_article";

    }

    @GetMapping (value = "/abroadList")
    public String abroadList(Model model, @PageableDefault Pageable pageable){

        Page<Review> paging = reviewService.getList(pageable);
        model.addAttribute("paging",paging);

        return "review/review_Abroad_List";

    }

    @GetMapping (value = "/jejuList")
    public String jejuList(){

        return "review/review_Jeju_List";

    }

    @GetMapping(value = "/delete/{id}")
    public String delete(Principal principal, @PathVariable("id") Long id){

        Review review = reviewService.getReview(id);

        reviewService.delete(review);

        return "redirect:/review/abroadList";

    }


    @GetMapping("/vote/{id}")
    public String reviewVote(Principal principal, @PathVariable("id") Long id){

        Review review = reviewService.getReview(id);
        Member member = memberService.getMember(principal.getName());
        reviewService.vote(review,member);
        return String.format("redirect:/review/article/%s",id);

    }

    @PostMapping("/reply/{id}")
    public  String writeReply(Model model, @PathVariable("id") Long id,
                             @Valid ReviewReplyForm reviewReplyForm, BindingResult bindingResult){

        Review review = reviewService.getReview(id);

        if(bindingResult.hasErrors()){
            model.addAttribute("review",review);
            return "/review/review_article";


        }

        Review_reply review_reply = reviewReplyService.create(review,reviewReplyForm.getContent());

        return String.format("redirect:/review/article/%s",review_reply.getId());

    }

}

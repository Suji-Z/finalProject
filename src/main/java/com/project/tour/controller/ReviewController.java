package com.project.tour.controller;


import com.project.tour.domain.Member;
import com.project.tour.domain.Review;
import com.project.tour.domain.ReviewForm;
import com.project.tour.service.MemberService;
import com.project.tour.service.ReviewService;
import com.project.tour.util.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping(value = "/update")
    public String update1(){

        return "review/review_write";

    }

    @PostMapping(value = "/update")
    public String update2(){

        return "redirect:/review/article";

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

    @GetMapping(value = "/delete")
    public String delete(){

        return "redirect:/review/review_abroadList";

    }

}

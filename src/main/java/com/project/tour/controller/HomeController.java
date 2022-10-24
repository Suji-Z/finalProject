package com.project.tour.controller;

import com.project.tour.domain.Member;
import com.project.tour.domain.Package;
import com.project.tour.oauth.dto.SessionUser;
import com.project.tour.oauth.service.LoginUser;
import com.project.tour.service.MemberService;
import com.project.tour.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final PackageService packageService;
    private final MemberService memberService;

/*
    @PostMapping("/main/keyword")
    @ResponseBody
    public Map<String, Object> mainKey(@RequestParam(value = "keyword", required = false) String keyword){
        Map<String, Object> result = new HashMap<String, Object>();
        System.out.println("keyword >>>" + keyword);
        List<Package> theme = packageService.getSearch(keyword);

        System.out.println("패키지 사이즈 >>>"+theme.size());

        //model.addAttribute("theme",theme);
        //model.addAttribute("keyword",keyword);
        result.put("theme", theme);
        result.put("returnKeyword",theme.get(0).getKeyword()); //ajax에 잘 넘어가나 확인용
        result.put("packageName",theme.get(0).getPackageName()); //ajax에 잘 넘어가나 확인용

        return result;
    }*/

    @GetMapping("/")
    public String main(Model model, ModelAndView mv, @LoginUser SessionUser user,
                       @RequestParam(value = "keyword", required = false) String keyword, Principal principal) {

        //model.addAttribute("posts",postsService.findAllDesc());

        System.out.println("메인키워드는: "+ keyword);
        String name = null;

        if(keyword==null) {

            keyword = "healing";

        }

        List<Package> recommend = new ArrayList<>();

        if(principal!=null) {

            if (memberService.existByEmail(principal.getName())) {

                Member member = memberService.getName(principal.getName());

                name = member.getName();

                List<String> userKeyword = Arrays.asList(member.getKeyword().split(","));

                recommend = packageService.getKeyword(userKeyword);

                Collections.shuffle(recommend);

                model.addAttribute("recommend",recommend);

            } else {

                name = user.getName();
            }
        }

        List<Package> theme = packageService.getSearch(keyword);
        Collections.shuffle(theme);

        System.out.println("패키지 사이즈"+theme.size());

        model.addAttribute("name",name);
        model.addAttribute("theme",theme);
        model.addAttribute("keyword",keyword);

        return "/main";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam(value = "keyword",required = false) String keyword){

        List<Package> theme = packageService.getSearch(keyword);

        System.out.println("패키지 사이즈"+theme.size());

        Collections.shuffle(theme);

        model.addAttribute("theme",theme);
        model.addAttribute("keyword",keyword);

        return "/main :: #viewTable";
    }
}
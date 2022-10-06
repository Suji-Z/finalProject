package com.project.tour.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {

    @GetMapping (value = "/")
    public String main(){

        return "mypage/mypage_main";

    }

    @GetMapping(value = "/cancelList")
    public String cancel_list(){

        return "mypage/mypage_bookingCancel_List";

    }
    @GetMapping(value = "/bookingList")
    public String booking_list(){

        return "mypage/mypage_bookingList";

    }

    @GetMapping(value = "/coupon")
    public String coupon(){

        return "mypage/mypage_coupon";

    }

    @GetMapping(value = "/point")
    public String point(){

        return "mypage/mypage_point";

    }

    @GetMapping(value = "/update")
    public String update(){

        return "mypage/mypage_profileUpdate";

    }

    @GetMapping(value = "/qna")
    public String qna(){

        return "mypage/mypage_q&a_list";

    }

    @GetMapping(value = "/reviewList")
    public String review_list(){

        return "mypage/mypage_reviewList";

    }
    @GetMapping(value = "/unregister")
    public String unregister(){

        return "mypage/mypage_unregister";

    }

    @GetMapping(value = "/voicecusList")
    public String voicecus_list(){

        return "mypage/mypage_voicecus_list";

    }
}

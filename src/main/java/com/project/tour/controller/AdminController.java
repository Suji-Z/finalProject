package com.project.tour.controller;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.project.tour.service.*;
import com.project.tour.util.PackageFileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

   private final AdminPackageService adminPackageService;

   private final AdminPackageDateService adminPackageDateService;

   private final UserBookingService userBookingService;

   private final MemberService memberService;

   private final MypageService mypageService;

    @GetMapping("/main")
    public String admin_main() {
        return "admin/admin_Main";
    }

    @GetMapping("/booking")
    public String admin_booking() {
        return "admin/admin_Booking";
    }


 //예약 회원 조회
    @GetMapping("/bookingUser")
    public String admin_bookingUser(Model model, @PageableDefault Pageable pageable,Package aPackage, Member member) {

        Page<UserBooking> paging = userBookingService.getBookingList(pageable);
        model.addAttribute("Member",member);
        model.addAttribute("Package",aPackage);
        model.addAttribute("paging",paging);

        return "admin/admin_BookingUser";
    }

    //패키지 상품 등록
    @GetMapping("/packageForm")
    public String createPackage(Model model) {
        model.addAttribute("packageCreate",new PackageCreate());
        return "admin/admin_Package";
    }

    @PostMapping("/packageForm")
    public String createPackagePost(PackageCreate packageCreate, @RequestParam("image1") MultipartFile multipartFile1,
                                     @RequestParam("image2") MultipartFile multipartFile2) throws IOException, ParseException {

        String fileName1 = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
        String fileName2 = StringUtils.cleanPath(multipartFile2.getOriginalFilename());

        packageCreate.setPreviewImage(fileName1);
        packageCreate.setDetailImage(fileName2);

        Package aPackage = adminPackageService.create(packageCreate);
        String uploadDir1 =  "package-preview/" + aPackage.getId();
        String uploadDir2 =  "package-detail/" + aPackage.getId();
        PackageFileUpload.saveFile1(uploadDir1,fileName1,multipartFile1);
        PackageFileUpload.saveFile2(uploadDir2,fileName2,multipartFile2);

        List<PackageDate > packageDate = adminPackageDateService.createDate(packageCreate,aPackage);


        return "redirect:/admin/packageList";
    }


    //패키지 리스트
    @GetMapping("/packageList")
    public String packageList(Model model, @PageableDefault Pageable pageable) {

        Page<Package> paging = adminPackageService.getList(pageable);
        model.addAttribute("paging",paging);

        return "admin/admin_PackageList";
    }

   //패키지 상품 삭제
    @GetMapping("/package/delete/{id}")
    public String packageDelete(@PathVariable("id") Long id) {

        Package aPackage = adminPackageService.getPackage(id);
        adminPackageService.delete(aPackage);

        return "redirect:/admin/packageList";
    }

    //패키지 상품 수정

   @GetMapping("/package/modify/{id}")
   public String packageModify(PackageCreate packageModify,
                               BindingResult bindingResult,@PathVariable("id") Long id ){

        Package aPackage = adminPackageService.getPackage(id);


       packageModify.setPackageName(aPackage.getPackageName());
        packageModify.setLocation1(aPackage.getLocation1());
        packageModify.setLocation2(aPackage.getLocation2());
        packageModify.setHotelName(aPackage.getHotelName());
        packageModify.setTransport(aPackage.getTransport());
        packageModify.setPackageInfo((aPackage.getPackageInfo()));
        packageModify.setCount(aPackage.getCount());
        packageModify.setPostStart(aPackage.getPostStart());
        packageModify.setPostEnd(aPackage.getPostEnd());
        packageModify.setTravelPeriod(aPackage.getTravelPeriod());
        packageModify.setKeyword(aPackage.getKeyword());
        packageModify.setPreviewImage(aPackage.getPreviewImage());
        packageModify.setDetailImage(aPackage.getDetailImage());



       return "admin/admin_Package";
    }

    @PostMapping("package/modify/{id}")
    public String packageModify(@Validated PackageCreate packageCreate, @PathVariable("id") Long id) {

        Package aPackage = adminPackageService.getPackage(id);

        adminPackageService.modify(aPackage,packageCreate);
        return "redirect:/admin/packageList";

    }

    @GetMapping
    public String bookingCheck(Model model, Principal principal, UserBookingForm userBookingForm,@PathVariable("id") Long id){

        UserBooking userBooking = userBookingService.getUserBooking(id);

        UserBooking userBooking1 = userBookingService.getUserBooking(id);
        model.addAttribute("userBooking",userBooking1);

        return "admin/admin_BookingUser";
    }

    @GetMapping("package/bookingCheck/{id}")
    public String bookingCheck(@Validated UserBookingForm userBookingForm, @PathVariable("id") Long id){

        UserBooking userBooking = userBookingService.getUserBooking(id);

        userBookingForm.setBookingStatus(1);
        adminPackageService.userBookingCheck(userBooking,userBookingForm);

        return "redirect:/admin/bookingUser";

    }

//회원 관리

    //회원 리스트
    @GetMapping("/user")
    public String userList(Model model, @PageableDefault Pageable pageable, Member member) {

        Page<Member> paging = memberService.getList(pageable);
        model.addAttribute("Member",member);
        model.addAttribute("paging",paging);

        return "admin/admin_UserList";
    }

    //회원 정보 수정(관리자용)
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/user/update/{email}")
    public String updateUser(Model model, Principal principal, MemberCreate memberCreate,@PathVariable("email") String eamil){

        Member member = memberService.getMember(String.valueOf(eamil));
        memberCreate.setBirth(member.getBirth());
        memberCreate.setEmail(member.getEmail());
        memberCreate.setName(member.getName());
        memberCreate.setEmail(member.getEmail());
        memberCreate.setPhone_num(member.getPhone());
        memberCreate.setPassword1(member.getPassword());


        String keywords = member.getKeyword();

        System.out.println(keywords);

        String words[] = keywords.split(",");

        String keyword = "";

        for(int i = 0;i<words.length;i++){
            keyword = words[i];

        }


        return "admin/admin_profileUpdate";

    }

    /* 추후 수정 (회원 수정, 탈퇴)

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/user/update/{email}")
    public String updateUser(@Valid MemberCreate memberCreate, BindingResult bindingResult,@PathVariable("email") String eamil){

        Member member = memberService.getMember(String.valueOf(eamil));

        mypageService.updateProfile(member,memberCreate.getName(),memberCreate.getBirth(), memberCreate.getKeyword());

        return "redirect:/admin/user";

    }

    //회원 탈퇴 시키기

    @GetMapping("/user/delete/{email}")
    public String packageDelete(@PathVariable("email") String eamil) {

        Member member = memberService.getMember(String.valueOf(eamil));
        adminPackageService.delete(aPackage);

        return "redirect:/admin/packageList";
    }
*/


//판매관련


    @GetMapping("admin_salespackage")
    public String admin_salespackage() {
        return "admin/admin_SalesPackage";
    }

    @GetMapping("admin_salespackagelist")
    public String admin_salespackagelist() {
        return "admin/admin_SalesPackageList";
    }

    @GetMapping("admin_salesuser")
    public String admin_salesuser() {
        return "admin/admin_salesUser";
    }

    @GetMapping("admin_salesuserlist")
    public String admin_salesuserlist() {
        return "admin/admin_SalesUserList";
    }
}
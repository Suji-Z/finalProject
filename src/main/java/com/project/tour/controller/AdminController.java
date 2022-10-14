package com.project.tour.controller;

import com.project.tour.domain.Package;
import com.project.tour.domain.PackageCreate;
import com.project.tour.domain.PackageDate;
import com.project.tour.service.AdminPackageDateService;
import com.project.tour.service.AdminPackageService;
import com.project.tour.util.PackageFileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

   private final AdminPackageService adminPackageService;

   private final AdminPackageDateService adminPackageDateService;


    @GetMapping("/main")
    public String admin_main() {
        return "admin/admin_Main";
    }

    @GetMapping("/booking")
    public String admin_booking() {
        return "admin/admin_Booking";
    }

    @PostMapping("/bookinguser")
    public String admin_bookinguser() {
        return "admin/admin_bookingUser";
    }

    //패키지 상품 등록
    @GetMapping("/packageForm")
    public String createPackage(Model model) {
        model.addAttribute("packageCreate",new PackageCreate());
        return "admin/admin_Package";
    }

    @PostMapping("/packageForm")
    public String createPackagePost(PackageCreate packageCreate, @RequestParam("image1") MultipartFile multipartFile1,
                                     @RequestParam("image2") MultipartFile multipartFile2) throws IOException {

        String fileName1 = StringUtils.cleanPath(multipartFile1.getOriginalFilename());
        String fileName2 = StringUtils.cleanPath(multipartFile2.getOriginalFilename());



        packageCreate.setPreviewImage(fileName1);
        packageCreate.setDetailImage(fileName2);

        Package aPackage = adminPackageService.create(packageCreate);

        PackageDate packageDate = adminPackageDateService.createDate(packageCreate,aPackage);
        String uploadDir1 =  "package-preview/" + aPackage.getId();
        String uploadDir2 =  "package-detail/" + aPackage.getId();

        PackageFileUpload.saveFile1(uploadDir1,fileName1,multipartFile1);
        PackageFileUpload.saveFile2(uploadDir2,fileName2,multipartFile2);

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
   public String packageModify(PackageCreate packageModify, BindingResult bindingResult,@PathVariable("id") Long id){

        Package aPackage = adminPackageService.getPackage(id);

        packageModify.setPackageName(aPackage.getPackageName());
        packageModify.setLocation1(aPackage.getLocation1());
        packageModify.setLocation2(aPackage.getLocation2());
        packageModify.setHotelName(aPackage.getHotelName());
        packageModify.setTransport(aPackage.getTransport());
        //가격 수정 어떻게 하지...
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

    @GetMapping("admin_userlist")
    public String admin_userlist() {
        return "admin/admin_UserList";
    }

}

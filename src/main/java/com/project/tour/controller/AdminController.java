package com.project.tour.controller;

import com.project.tour.domain.Package;
import com.project.tour.domain.PackageCreate;
import com.project.tour.domain.PackageDate;
import com.project.tour.service.AdminPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

   private final AdminPackageService adminPackageService;


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

    @GetMapping("/packageForm")
    public String createPackage(Model model) {
        model.addAttribute("packageCreate",new PackageCreate());
        return "admin/admin_Package";
    }

    @PostMapping("/packageForm")
    public String createPackagePost(PackageCreate packageCreate, PackageDate packageDate) {
        adminPackageService.create(packageCreate,packageDate);
        return "redirect:/admin/packageList";
    }

    @GetMapping("/packageList")
    public String packageList(Model model, @PageableDefault Pageable pageable) {

        Page<Package> paging = adminPackageService.getList(pageable);
        model.addAttribute("paging",paging);

        return "admin/admin_PackageList";
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

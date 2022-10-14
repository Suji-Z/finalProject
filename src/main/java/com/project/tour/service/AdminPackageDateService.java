package com.project.tour.service;

import com.project.tour.domain.Package;
import com.project.tour.domain.PackageCreate;
import com.project.tour.domain.PackageDate;
import com.project.tour.repository.AdminPackageDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminPackageDateService {
    private final AdminPackageDateRepository adminPackageDateRepository;

    public PackageDate createDate(PackageCreate packageCreate, Package packages)      {
        PackageDate packageDate = new PackageDate();

        packageDate.setAprice(packageCreate.getAprice());
        packageDate.setBprice(packageCreate.getBprice());
        packageDate.setCprice(packageCreate.getCprice());
        packageDate.setDiscount(packageCreate.getDiscount());
        packageDate.setPackages(packages);
        packageDate.setRemaincount(packageCreate.getCount());

        return adminPackageDateRepository.save(packageDate);
    }

//    public void departureDate(Package packages) {
//
//        PackageDate packageDate = new PackageDate();
//        Package aPackage = new Package();
//
//    }

}

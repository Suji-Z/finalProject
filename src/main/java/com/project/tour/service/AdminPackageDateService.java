package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Package;
import com.project.tour.domain.PackageCreate;
import com.project.tour.domain.PackageDate;
import com.project.tour.repository.AdminPackageDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AdminPackageDateService {
    private final AdminPackageDateRepository adminPackageDateRepository;

    public PackageDate createDate(PackageCreate packageCreate, Package packages){


        PackageDate packageDate = new PackageDate();

        String postStart = packages.getPostStart();
        String postEnd = packages.getPostEnd();

        int startYear = Integer.parseInt(postStart.substring(0,4));
        int startMonth = Integer.parseInt(postStart.substring(4,6));
        int startDate = Integer.parseInt(postStart.substring(6,8));

        Calendar cal = Calendar.getInstance();

        cal.set(startYear,startMonth-1,startDate);

        while(true) {

           cal.add(Calendar.DATE,1); //하루씩 증가

            if(Integer.parseInt(postStart)>Integer.parseInt(postEnd));
            break;
        }

        packageDate.setAprice(packageCreate.getAprice());
        packageDate.setBprice(packageCreate.getBprice());
        packageDate.setCprice(packageCreate.getCprice());
        packageDate.setDiscount(packageCreate.getDiscount());
        packageDate.setPackages(packages);
        packageDate.setRemaincount(packageCreate.getCount());


        return adminPackageDateRepository.save(packageDate);


    }

public PackageDate getDate(Date id) {
    Optional<PackageDate> op =  adminPackageDateRepository.findById(Long.valueOf(String.valueOf(id))); //하나의 데이터 읽어옴

    if(op.isPresent())
        return op.get();
    else
        throw new DataNotFoundException("데이터가 없습니다.");


}



}

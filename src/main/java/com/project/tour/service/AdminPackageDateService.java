package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Package;
import com.project.tour.domain.PackageCreate;
import com.project.tour.domain.PackageDate;
import com.project.tour.repository.AdminPackageDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AdminPackageDateService {
    private final AdminPackageDateRepository adminPackageDateRepository;

    public List<PackageDate> createDate(PackageCreate packageCreate, Package packages) throws ParseException, IOException {


        String match = "-";
        String postStart = packages.getPostStart().replaceAll(match, "");
        String postEnd = packages.getPostEnd().replaceAll(match, "");

        //endDate-startDate 게시기간 int형변환해서

        String s = postStart;
        String e = postEnd;

        DateFormat format = new SimpleDateFormat("yyyyMMdd");

        Date start = format.parse(s);
        Date end = format.parse(e);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        Calendar cal3 = Calendar.getInstance();;



        cal1.setTime(start);
        cal2.setTime(end);

        cal3.setTime(cal1.getTime());
        cal3.add(Calendar.DATE,-1);


        long a = (end.getTime() - start.getTime())/(1000*60*60*24);

        System.out.println(a);

        List<PackageDate> packageDates = new ArrayList<>();

        for (int i =0;i<=a; i++) {
            PackageDate packageDate = new PackageDate();

            System.out.println(a);
            packageDate.setAprice(packageCreate.getAprice());
            packageDate.setBprice(packageCreate.getBprice());
            packageDate.setCprice(packageCreate.getCprice());
            packageDate.setDiscount(packageCreate.getDiscount());
            packageDate.setPackages(packages);
            packageDate.setRemaincount(packageCreate.getCount());
            //  cal1.add(Calendar.DATE, 1);

            if(cal1.before(cal2)) {
                cal3.add(Calendar.DATE, 1);
                packageDate.setDeparture(format.format(cal3.getTime()));
            }


            System.out.println(packageDate.getDeparture());

//            System.out.println(cal1);
//            System.out.println(start);
//            packageDate.setDeparture();

            packageDates.add(packageDate);
        }
        return adminPackageDateRepository.saveAll(packageDates);
    }
//출발일 삭제
    public void deleteDate(Integer id) {
        PackageDate packageDate = adminPackageDateRepository.findById(id).get();
        adminPackageDateRepository.delete(packageDate);
    }

    //출발일 수정

    public void modifyDate (PackageDate packageDate, PackageCreate packageCreate) {
        packageDate.setAprice(packageCreate.getAprice());
        packageDate.setBprice(packageCreate.getBprice());
        packageDate.setCprice(packageCreate.getCprice());
        packageDate.setDiscount(packageCreate.getDiscount());
        adminPackageDateRepository.save(packageDate);

    }

    public PackageDate getDate(Integer id) {
        Optional<PackageDate> op = adminPackageDateRepository.findById(id); //하나의 데이터 읽어옴

        if (op.isPresent())
            return op.get();
        else
            throw new DataNotFoundException("데이터가 없습니다.");


    }

    public Page<PackageDate> getList(Long id,Pageable pageable){

        List<Sort.Order> sorts = new ArrayList<Sort.Order>();
        sorts.add(Sort.Order.desc("id"));

        pageable= PageRequest.of(
                pageable.getPageNumber()<=0?0:
                        pageable.getPageNumber()-1,
                pageable.getPageSize(),Sort.by(sorts));

        return adminPackageDateRepository.findByPackages_Id(id,pageable);
    }
}

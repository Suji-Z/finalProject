package com.project.tour.service;

import com.project.tour.controller.DataNotFoundException;
import com.project.tour.domain.Package;
import com.project.tour.domain.PackageDate;
import com.project.tour.repository.PackageDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PackageDateService {

    private final PackageDateRepository packageDateRepository;

    //특정 packageNum과 departureDate로 가격 출력(임시)
    // packageNum비교 타입 몰라서 멈춤 일단 id랑 비교함
    public PackageDate getPackageDate(Package packages, String departureDate) {

        Optional<PackageDate> packageDate =
                packageDateRepository.findByPackagesAndDeparture(packages, departureDate);

            return packageDate.get();

    }
    
    /**
     * 상세페이지에서 날짜/인원별 가격출력
     * */
    public PackageDate getPrice(Long id,String selectdate){

        Optional<PackageDate> packageDate = packageDateRepository.findByPackages_IdAndDeparture(id, selectdate);

        if(packageDate.isPresent())
            return packageDate.get();
        else
            throw new DataNotFoundException("package Not Found");

    }

}



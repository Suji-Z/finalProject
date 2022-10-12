package com.project.tour.service;

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

    private PackageDateRepository packageDateRepository;

    //특정 packageNum과 departureDate로 가격 출력(임시)
    public PackageDate getPackageDate(long packageNum, LocalDateTime departureDate){
        Optional<PackageDate> packageDateData = packageDateRepository.findById(packageNum);
        return packageDateData.get();
    }
}

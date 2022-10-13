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

    private final PackageDateRepository packageDateRepository;

    //특정 packageNum과 departureDate로 가격 출력(임시)
    // packageNum비교 타입 몰라서 멈춤 일단 id랑 비교함
    public PackageDate getPackageDate(long packageNum, String departureDate) {

        Optional<PackageDate> packageDate =
                packageDateRepository.findByIdAndDeparture(packageNum, departureDate);

            return packageDate.get();

    }
}



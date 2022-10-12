package com.project.tour.repository;

import com.project.tour.domain.Package;
import com.project.tour.domain.PackageDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PackageDateRepository extends JpaRepository<Package,Long> {

    //임시 : departureDate랑 PackageNum으로 데이터 가져오는 코드 필요
    Optional<PackageDate> findById(long id);
}

package com.project.tour.repository;

import com.project.tour.domain.Package;
import com.project.tour.domain.PackageDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PackageDateRepository extends JpaRepository<PackageDate,Long> {

    //임시 : departureDate랑 PackageNum으로 데이터 가져오는 코드 필요
    Optional<PackageDate> findByIdAndDeparture(int packageNum,String departureDate);

    List<PackageDate> findByDepartureBetweenAndApriceBetweenAndRemaincountGreaterThanEqual(String startDeparture, String endDeparture, int startprice, int endprice, int remaincount);
    List<PackageDate> findByDepartureBetweenAndApriceGreaterThanEqualAndRemaincountGreaterThanEqual(String startDeparture, String endDeparture, Integer price,int remaincount);

}

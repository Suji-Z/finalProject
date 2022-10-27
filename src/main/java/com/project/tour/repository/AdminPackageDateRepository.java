package com.project.tour.repository;

import com.project.tour.domain.Package;
import com.project.tour.domain.PackageDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminPackageDateRepository extends JpaRepository<PackageDate, Integer> {
    Page<PackageDate> findAll(Pageable pageable);
    Page<PackageDate> findByPackages_Id(Long id,Pageable pageable);

    Optional<PackageDate> findAllByDepartureAndPackages_Id(String departure, Long packageNum);



}

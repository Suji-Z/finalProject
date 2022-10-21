package com.project.tour.repository;

import com.project.tour.domain.Package;
import com.project.tour.domain.PackageDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPackageDateRepository extends JpaRepository<PackageDate, Integer> {
    Page<PackageDate> findAll(Pageable pageable);
    Page<PackageDate> findByPackages_Id(Long id,Pageable pageable);


}

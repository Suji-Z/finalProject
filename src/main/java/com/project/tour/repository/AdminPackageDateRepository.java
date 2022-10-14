package com.project.tour.repository;

import com.project.tour.domain.PackageDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPackageDateRepository extends JpaRepository<PackageDate, Long> {
}

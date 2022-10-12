package com.project.tour.repository;

import com.project.tour.domain.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<Package,Long> {

    Optional<Package> findById(long id);

}

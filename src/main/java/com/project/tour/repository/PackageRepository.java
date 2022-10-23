package com.project.tour.repository;

import com.project.tour.domain.Package;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PackageRepository extends JpaRepository<Package,Long> {

    Optional<Package> findById(long id);
    Package findByPackageName(String packageName);
    Package findByKeyword(String keyword);

    Page<Package> findAll(Pageable pageable);

    List<Package> findByIdIn(List<Long> packageNums);
    Page<Package> findByIdIn(Set<Long> packageNums, Pageable pageable);

}

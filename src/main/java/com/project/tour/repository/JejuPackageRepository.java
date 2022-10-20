package com.project.tour.repository;

import com.project.tour.domain.Package;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface JejuPackageRepository extends JpaRepository<Package,Long>, JpaSpecificationExecutor<Package> {
//Specification을 인자로 받는 findAll 함수를 사용하기 위해서는 인터페이스에 JpaSpecificationExecutor를 상속받아야 한다.

    @Override
    Page<Package> findAll(Specification<Package> spec, Pageable pageable);
}

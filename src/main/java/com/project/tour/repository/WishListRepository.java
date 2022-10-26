package com.project.tour.repository;

import com.project.tour.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    List<WishList> findByMember_Id(Long id);

    Optional<WishList> findByMember_IdAndPackages_Id(Long id1, Long id2);
}

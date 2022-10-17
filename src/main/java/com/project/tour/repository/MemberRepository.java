package com.project.tour.repository;

import com.project.tour.domain.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByName(String name);

    boolean existsByEmail(String email);

    Optional<Member> findByNameAndPhone(String name,String phone);

    Member findByEmailAndPhone(String email,String phone);


}

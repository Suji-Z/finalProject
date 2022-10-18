package com.project.tour.oauth.dto;

import com.project.tour.oauth.model.BaseAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BaseAuthUserRepository extends JpaRepository<BaseAuthUser,Long> {

    Optional<BaseAuthUser> findByEmail(String email);
}

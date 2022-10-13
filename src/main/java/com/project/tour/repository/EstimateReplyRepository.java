package com.project.tour.repository;

import com.project.tour.domain.EstimateReply;
import com.project.tour.domain.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstimateReplyRepository extends JpaRepository<EstimateReply, Long> {



}


        /* 단순 조건
        public School findByName(String name); // where name = ?
        public List<School> findByRegion(String region); // where region = ?

        // not
        public List<School> findByRegionNot(String region); // where region <> ?

        // and
        public List<School> findByNameAndRanking(String name, int ranking); // where name = ? and ranking = ?

        // or
        public List<School> findByNameOrRanking(String name, int ranking); // where name = ? or ranking = ?

        // between
        public List<School> findByRankingBetween(int startRanking, int endRanking); // where ranking between ? and ?

        // 부등호
        public List<School> findByRankingLessThan(int ranking); // where ranking < ?
        public List<School> findByRankingLessThanEqual(int ranking); // where ranking <= ?
        public List<School> findByRankingGreaterThan(int ranking); // where ranking > ?

        // null
        public List<School> findByRankingIsNull(); // where ranking is null
        public List<School> findByRankingIsNotNull(); // where ranking is not null

        // like
        public List<School> findByNameStartingWith(String name); // where name like ?%
        public List<School> findByNameEndingWith(String name); // where name like %?
        public List<School> findByNameContaining(String name); // where name like %?%

        // in
        public List<School> findByIdIn(List<Integer> id); // where school_id in (?, ?, ? ...)
        public List<School> findByIdNotIn(List<Integer> id); // where school_id not in (?, ?, ? ...)

        // order by
        public List<School> findAllByOrderById(); // order by school_id, "AllBy" 주의
        public List<School> findByNameContainingOrderByRanking(String name); // where name like %?% order by ranking
        public List<School> findByIdOrderByIdDesc(int id); // where id = ? order by id desc
         */


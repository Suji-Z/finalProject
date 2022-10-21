package com.project.tour.repository;

import com.project.tour.domain.Package;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.util.List;

public class JejuSpecification {
// equal 말고도 like, lessThanOrEqualTo, greaterThanOrEqualTo 등 다양한 메서드를 지원합니다.

    //출발일
    public static Specification<Package> greaterThanOrEqualToDeparture(String departure) {
        // Person의 firstName과 파라미터의 firstName을 비교
        // 해당 람다식은 Specification의 toPredicate() 메소드를 구현한 것
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root
                        .join("packagedatelist", JoinType.LEFT)
                        .get("departure"), departure);
    }


    //잔여수량
    public static Specification<Package> greaterThanOrEqualToRemaincount(Integer count) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root
                        .join("packagedatelist", JoinType.LEFT)
                        .get("remaincount"), count);

    }

    //지역2
    public static Specification<Package> equalLocation2(String location) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("location2"), location);
    }

    //지역1
    public static Specification<Package> equalLocation1(String location) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("location1"), location);
    }

    //키워드
    public static Specification<Package> equalKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("keyword"), keyword);
    }

    //항공사
    public static Specification<Package> equalTransport(List<String> transport){
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get("transport")).value(transport);
    }

}

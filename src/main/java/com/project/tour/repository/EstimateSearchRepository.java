package com.project.tour.repository;

import com.project.tour.domain.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.project.tour.domain.QPackageDate.packageDate;
import static com.project.tour.domain.QPackage.package$;

@Repository
@RequiredArgsConstructor
public class EstimateSearchRepository {
    @Autowired
    private final JPAQueryFactory queryFactory;

    public List<EstimateSearchDTO> searchByWhere(EstimateSearchCondition condition) {

        return queryFactory
                .select(new QEstimateSearchDTO(
                        package$.id,
                        package$.packageName,
                        package$.previewImage,
                        packageDate.aprice.min()
                )).distinct()
                .from(package$)
                .leftJoin(package$.packagedatelist, packageDate)
                .where(
                        location2Eq(condition.getLcoation2()),
                        remaincountGoe(condition.getRemaincount()),
                        priceEq(condition.getPrice()),
                        priceGoe(condition.getMinPrice()),
                        priceLoe(condition.getMaxPrice()),
                        startDayEq(condition.getStartday()),
                        flxstartday1Goe(condition.getFlxstartday1()),
                        flxstartday2Loe(condition.getFlxstartday2()),
                        travelPeriodEq(condition.getTravelPeriod())
                )
                .groupBy(package$.id,package$.previewImage,packageDate.aprice)
                .fetch();
    }

    private BooleanExpression location2Eq(String location2) {
        return StringUtils.hasText(location2) ? package$.location2.eq(location2) : null;
    }

    private BooleanExpression remaincountGoe(Integer remaincount) {
        return remaincount != null ? packageDate.remaincount.goe(remaincount) : null;
    }

    private BooleanExpression priceEq(Integer price) {
        return price != null ? packageDate.aprice.eq(price) : null;
    }

    private BooleanExpression priceGoe(Integer minPrice) {
        return minPrice != null ? packageDate.aprice.goe(minPrice) : null;
    }

    private BooleanExpression priceLoe(Integer maxPrice) {
        return maxPrice != null ? packageDate.aprice.loe(maxPrice) : null;
    }

    private BooleanExpression startDayEq(String startDay) {
        return StringUtils.hasText(startDay) ? packageDate.departure.eq(startDay) : null;
    }

    private BooleanExpression flxstartday1Goe(String flxstartday1) {
        return StringUtils.hasText(flxstartday1) ? packageDate.departure.goe(flxstartday1) : null;
    }

    private BooleanExpression flxstartday2Loe(String flxstartday2) {
        return StringUtils.hasText(flxstartday2) ? packageDate.departure.loe(flxstartday2) : null;
    }

    private BooleanExpression travelPeriodEq(Integer Travelperiod) {
        return Travelperiod != null ? package$.travelPeriod.eq(Travelperiod) : null;
    }

}

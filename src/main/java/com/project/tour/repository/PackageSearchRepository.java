package com.project.tour.repository;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.project.tour.domain.QPackageDate.packageDate;
import static com.project.tour.domain.QPackage.package$;
import static com.project.tour.domain.QShortReview.shortReview;

@Repository
@RequiredArgsConstructor
public class PackageSearchRepository {

    @Autowired
    private final JPAQueryFactory queryFactory;

    public Page<PackageSearchDTO> searchByWhere(PackageSearchCondition condition, Pageable pageable) {

        //가져올데이터
        List<PackageSearchDTO> content = queryFactory
                .select(new QPackageSearchDTO(
                        package$.id,
                        package$.packageName,
                        package$.previewImage,
                        package$.location2,
                        package$.packageInfo,
                        package$.postStart,
                        package$.postEnd,
                        package$.travelPeriod,
                        package$.keyword,
                        packageDate.aprice,
                        packageDate.discount,
                        package$.count,
                        package$.hitCount,
                        shortReview.count(),
                        shortReview.score.sum()


                ))
                .from(package$)
                .leftJoin(package$.packagedatelist, packageDate)
                .leftJoin(shortReview).on(shortReview.packages.id.eq(package$.id))
                .where(
                    location1Eq(condition.getLocation1()),
                    location2Eq(condition.getLocation2()),
                    priceGoe(condition.getPricerangestr()),
                    priceLoe(condition.getPricerangeend()),
                    startDayEq(condition.getStartday()),
                    transportIn(condition.getTransport()),
                    travelPeriodIn(condition.getTravelPeriod()),
                    remaincountGoe(condition.getTotcount()),
                    keywordEq(condition.getKeyword())
                )
                .groupBy(package$.id,shortReview.packages.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(package$.id.desc())
                .fetch();

        //페이징처리에 필요한 count
        JPAQuery<Long> count = queryFactory
                .select(package$.count())
                .from(package$)
                .where(
                        package$.id.in(
                                JPAExpressions
                                .select(package$.id).distinct()
                                .from(package$)
                                .leftJoin(package$.packagedatelist, packageDate)
                                .where(
                                        location1Eq(condition.getLocation1()),
                                        location2Eq(condition.getLocation2()),
                                        priceGoe(condition.getPricerangestr()),
                                        priceLoe(condition.getPricerangeend()),
                                        startDayEq(condition.getStartday()),
                                        transportIn(condition.getTransport()),
                                        travelPeriodIn(condition.getTravelPeriod()),
                                        remaincountGoe(condition.getTotcount()),
                                        keywordEq(condition.getKeyword())
                                )
                        )
                );

        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchOne());
    }

    private BooleanExpression location1Eq(String location1) {
        return StringUtils.hasText(location1) ? package$.location1.eq(location1) : null;
    }
    private BooleanExpression location2Eq(String location2) {
        return StringUtils.hasText(location2) ? package$.location2.eq(location2) : null;
    }
    private BooleanExpression startDayEq(String startDay) {
        return StringUtils.hasText(startDay) ? packageDate.departure.eq(startDay) : null;
    }
    private BooleanExpression priceGoe(Integer pricerangestr) {
        return pricerangestr != null ? packageDate.aprice.goe(pricerangestr) : null;
    }
    private BooleanExpression priceLoe(Integer pricerangeend) {
        return pricerangeend != null ? packageDate.aprice.loe(pricerangeend) : null;
    }
    private BooleanExpression travelPeriodIn(List<Integer> Travelperiod) {
        return Travelperiod != null ? package$.travelPeriod.in(Travelperiod) : null;
    }
    private BooleanExpression transportIn(List<String> transport) {
        return transport != null ? package$.transport.in(transport) : null;
    }
    private BooleanExpression remaincountGoe(Integer totcount) {
        return totcount != null ?  packageDate.remaincount.goe(totcount) : null;
    }
    private BooleanExpression keywordEq(String keyword) {
        return StringUtils.hasText(keyword) ? package$.keyword.eq(keyword) : null;
    }
}

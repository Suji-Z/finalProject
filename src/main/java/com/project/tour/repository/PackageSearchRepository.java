package com.project.tour.repository;

import com.project.tour.domain.*;
import com.project.tour.domain.Package;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.tour.domain.QPackageDate.packageDate;
import static com.project.tour.domain.QPackage.package$;

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
                        packageDate.discount
                ))
                .from(package$)
                .leftJoin(package$.packagedatelist, packageDate)
                .where(


                )
                .groupBy()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        //페이징처리에 필요한 count
        JPAQuery<Long> count = queryFactory
                .select(package$.count())
                .from(package$)
                .leftJoin(package$.packagedatelist, packageDate)
                .where(

                );


        return PageableExecutionUtils.getPage(content, pageable, () -> count.fetchOne());

    }
}

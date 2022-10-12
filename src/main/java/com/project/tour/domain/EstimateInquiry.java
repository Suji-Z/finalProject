package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@DynamicInsert
public class EstimateInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EstimateNum")
    private Long id;

    private String email;

    @Column(length = 500)
    private String title;

    private String location1;
    private String location2;

    private Integer aCount;

    @ColumnDefault("0")
    private Integer bCount;

    @ColumnDefault("0")
    private Integer cCount;

    private String startDay;

    private String endDay;

    private String price;

    private Boolean flexibleDay;

    @Column(columnDefinition = "TEXT",length = 3000)
    private String content;

    private LocalDateTime created;

    /**하나의 문의글에 여러개의 답변
     * 문의글 삭제시 해당글의 답변도 함께 삭제되게 구현
     * */
    @OneToMany(mappedBy = "estimateInquiry",cascade = CascadeType.REMOVE)
    private List<EstimateReply> EstimateReplyList;

}

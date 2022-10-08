package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class EstimateInquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EstimateNum")
    private Integer id;

    private String email;

    @Column(length = 500)
    private String title;

    private String location;

    private Integer a_count;

    private Integer b_count;

    private Integer c_count;

    private String startDay;

    private String endDay;

    private Integer price;

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

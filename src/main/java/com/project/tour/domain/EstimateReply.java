package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class EstimateReply {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long EstimateReplyNum;

    private String subject;
    private String content;
    private LocalDateTime created;
    private Integer layer;
    private Integer parent;


}

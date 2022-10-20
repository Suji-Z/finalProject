package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
public class NoticeReplyForm {

    private Long id;

    private String name;

    private LocalDateTime created;

    private String content;

}

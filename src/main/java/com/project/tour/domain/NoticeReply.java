package com.project.tour.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class NoticeReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeReplyNum")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "memberNum")
    @JsonBackReference
    private Member member;

    private LocalDateTime created;

    private String content;

    @ManyToOne
    @JoinColumn(name = "noticeNum")
    @JsonBackReference
    private Notice notice;



}

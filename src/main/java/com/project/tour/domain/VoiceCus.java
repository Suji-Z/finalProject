package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class VoiceCus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String types;

    @ManyToOne
    private Member author;

    @OneToMany(mappedBy = "voiceCus",cascade = CascadeType.REMOVE)
    private List<VoiceCusReply> voiceCusReplyList;

    private LocalDateTime createdDate;

}

package com.project.tour.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Notice {

    @Id
    Long id;

    String category;

    String subject;

    String content;

    LocalDateTime created;



}

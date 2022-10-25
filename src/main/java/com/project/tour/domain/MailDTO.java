package com.project.tour.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MailDTO {
    private String addressdd;
    private String title;
    private String message;
    private String fromAddress;
}

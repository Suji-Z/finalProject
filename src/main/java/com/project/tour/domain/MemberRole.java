package com.project.tour.domain;

import lombok.Getter;

@Getter
public enum MemberRole {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    GUEST("ROLE_GUEST");

    private String value;

    MemberRole(String value){
        this.value = value;
    }
}

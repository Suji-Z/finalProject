package com.project.tour.oauth.dto;

import com.project.tour.oauth.model.BaseAuthUser;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    public SessionUser(BaseAuthUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}

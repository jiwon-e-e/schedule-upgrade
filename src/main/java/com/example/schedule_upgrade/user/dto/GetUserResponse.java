package com.example.schedule_upgrade.user.dto;

import lombok.Getter;

@Getter
public class GetUserResponse {
    private final Long id;
    private final String name;
    private final String email;

    public GetUserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

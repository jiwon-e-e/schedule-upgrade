package com.example.schedule_upgrade.user.dto;

import lombok.Getter;

@Getter
public class UpdateUserResponse {
    private final String name;

    public UpdateUserResponse(String name) {
        this.name = name;
    }
}

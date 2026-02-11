package com.example.schedule_upgrade.user.dto;

import lombok.Getter;

@Getter
public class SignupResponse {
    private final String name;
    private final String email;

    public SignupResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }
}

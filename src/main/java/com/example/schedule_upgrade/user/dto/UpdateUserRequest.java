package com.example.schedule_upgrade.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @Size(max=10)
    private String name;
}

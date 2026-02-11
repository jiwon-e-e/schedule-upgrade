package com.example.schedule_upgrade.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @NotBlank
    @Size(max=10)
    private String name;
}

package com.example.schedule_upgrade.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Pattern(regexp = "/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$/\n")
    private String email;

    @NotBlank
    private String pw;
}

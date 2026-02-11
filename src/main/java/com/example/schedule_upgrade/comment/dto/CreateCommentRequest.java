package com.example.schedule_upgrade.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @NotBlank
    @Size (max = 100)
    private String content;
}

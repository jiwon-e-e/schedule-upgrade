package com.example.schedule_upgrade.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {
    private final Long id;
    private final String user_name;
    private final String content;
    private final LocalDateTime createdAt;

    public CreateCommentResponse(Long id, String user_name, String content, LocalDateTime createdAt) {
        this.id = id;
        this.user_name = user_name;
        this.content = content;
        this.createdAt = createdAt;
    }
}

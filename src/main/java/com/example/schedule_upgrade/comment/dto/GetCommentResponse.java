package com.example.schedule_upgrade.comment.dto;

import lombok.Getter;

@Getter
public class GetCommentResponse {
    private final Long id;
    private final String user_name;
    private final String content;

    public GetCommentResponse(Long id, String user_name, String content) {
        this.id = id;
        this.user_name = user_name;
        this.content = content;
    }
}

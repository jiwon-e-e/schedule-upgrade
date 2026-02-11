package com.example.schedule_upgrade.schedule.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.schedule_upgrade.comment.dto.GetCommentResponse;
import lombok.Getter;

@Getter
public class GetOneScheduleResponse {
    private final Long id;
    private final String user_name;
    private final String title;
    private final String content;
    private final List<GetCommentResponse> commentList;

    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public GetOneScheduleResponse(Long id, String user_name, String title, String content, List<GetCommentResponse> commentList, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.user_name = user_name;
        this.title = title;
        this.content = content;
        this.commentList = commentList;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}

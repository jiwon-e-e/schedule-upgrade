package com.example.schedule_upgrade.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateScheduleResponse {
    private final Long id;
    private final String user_name;
    private final String title;
    private final String content;

    private final LocalDateTime modifiedAt;

    public UpdateScheduleResponse(Long id, String user_name, String title, String content, LocalDateTime modifiedAt) {
        this.id = id;
        this.user_name = user_name;
        this.title = title;
        this.content = content;
        this.modifiedAt = modifiedAt;
    }
}

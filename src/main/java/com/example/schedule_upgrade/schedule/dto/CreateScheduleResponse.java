package com.example.schedule_upgrade.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateScheduleResponse {
    private final Long id;
    private final String user_name;
    private final String title;
    private final String content;

    private final LocalDateTime createdAt;

    public CreateScheduleResponse(Long id, String user_name, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.user_name = user_name;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}

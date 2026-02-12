package com.example.schedule_upgrade.schedule.dto;

import lombok.Getter;

@Getter
public class GetSchedulesResponse {
    private final Long id;
    private final String title;
    private final int commentCount;

    public GetSchedulesResponse(Long id, String title, int commentCount) {
        this.id = id;
        this.title = title;
        this.commentCount = commentCount;
    }
}

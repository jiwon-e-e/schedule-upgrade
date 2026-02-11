package com.example.schedule_upgrade.schedule.dto;

import lombok.Getter;

@Getter
public class GetSchedulesResponse {
    private final Long id;
    private final String title;

    public GetSchedulesResponse(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}

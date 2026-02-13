package com.example.schedule_upgrade.schedule.controller;

import com.example.schedule_upgrade.global.exception.ErrorCode;
import com.example.schedule_upgrade.global.exception.ServiceException;
import com.example.schedule_upgrade.schedule.dto.*;
import com.example.schedule_upgrade.schedule.service.ScheduleService;
import com.example.schedule_upgrade.user.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    ResponseEntity<CreateScheduleResponse> create
            (@Valid @RequestBody CreateScheduleRequest request,
             HttpSession session){

        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new ServiceException(ErrorCode.BEFORE_LOGIN);
        }

        CreateScheduleResponse response = scheduleService.createSchedule(request,sessionUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/schedules")
    ResponseEntity<List<GetSchedulesResponse>> getAll(
            @Valid @RequestParam(defaultValue = "0") int page,
            @Valid @RequestParam(defaultValue = "5") int size
    ){
        List<GetSchedulesResponse> response = scheduleService.findAll(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/schedules/{scheduleId}")
    ResponseEntity<GetOneScheduleResponse> getOne(
            @Valid @PathVariable Long scheduleId,
            @Valid @RequestParam(defaultValue = "0") int page,
            @Valid @RequestParam(defaultValue = "5") int size){
        GetOneScheduleResponse response = scheduleService.findOne(scheduleId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/schedules/{scheduleId}")
    ResponseEntity<UpdateScheduleResponse> update(
            @PathVariable Long scheduleId, @Valid @RequestBody UpdateScheduleRequest request,
            HttpSession session){

        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new ServiceException(ErrorCode.BEFORE_LOGIN);
        }

        UpdateScheduleResponse response = scheduleService.update(scheduleId, request, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/schedules/{scheduleId}")
    ResponseEntity<String> delete(
            @Valid @PathVariable Long scheduleId, HttpSession session){

        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new ServiceException(ErrorCode.BEFORE_LOGIN);
        }

        scheduleService.delete(scheduleId,sessionUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("일정 삭제가 정상적으로 완료되었습니다.");
    }

}

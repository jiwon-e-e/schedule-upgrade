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

    // 대부분 요청에 HttpSession 을 받아와서 sessionUser 를 확인하는 과정이 추가되었습니다.
    // 로그인이 꼭 필요한 요청은 sessionUser 가 null 이라면 Exception 으로 처리됩니다.

    @PostMapping("/schedules")
    ResponseEntity<CreateScheduleResponse> create
            (@Valid @RequestBody CreateScheduleRequest request,
             HttpSession session){

        // 이미 sessionUser 가 존재한다는... 걸 검사함
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new ServiceException(ErrorCode.BEFORE_LOGIN);
        }

        CreateScheduleResponse response = scheduleService.createSchedule(request,sessionUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET 요청에 page의 크기와 번호를 지정할 수 있습니다.
    // size-> 한 페이지당 몇 개의 정보가 조회되는지 지정
    // page-> 몇 번째 페이지를 조회할건지 지정

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

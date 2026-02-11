package com.example.schedule_upgrade.schedule.service;

import com.example.schedule_upgrade.comment.dto.GetCommentResponse;
import com.example.schedule_upgrade.comment.entity.Comment;
import com.example.schedule_upgrade.comment.repository.CommentRepository;
import com.example.schedule_upgrade.exception.NonExistentException;
import com.example.schedule_upgrade.exception.UnauthorizedUserException;
import com.example.schedule_upgrade.schedule.dto.*;
import com.example.schedule_upgrade.schedule.entity.Schedule;
import com.example.schedule_upgrade.schedule.repository.ScheduleRepository;
import com.example.schedule_upgrade.user.entity.User;
import com.example.schedule_upgrade.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final ResourcePatternResolver resourcePatternResolver;

    @Transactional
    public CreateScheduleResponse createSchedule(@Valid CreateScheduleRequest request, Long sessionUserId) {
        User user = userRepository.findById(sessionUserId).orElseThrow(
                ()->new NonExistentException("존재하지 않는 사용자입니다.")
        );

        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                user
        );

        Schedule newSchedule = scheduleRepository.save(schedule);

        return new CreateScheduleResponse(
                newSchedule.getId(),
                newSchedule.getUser().getName(),
                newSchedule.getTitle(),
                newSchedule.getContent(),
                newSchedule.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<GetSchedulesResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        List<GetSchedulesResponse> dtos = new ArrayList<>();

        for (Schedule schedule : schedules) {
            GetSchedulesResponse dto = new GetSchedulesResponse(
                    schedule.getId(),
                    schedule.getTitle()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    @Transactional(readOnly = true)
    public GetOneScheduleResponse findOne(@Valid Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                ()-> new NonExistentException("존재하지 않는 일정입니다.")
        );

        List<Comment> commentList = commentRepository.findAllById(schedule.getId());

        List<GetCommentResponse> dtos = new ArrayList<>();
        for (Comment comment : commentList) {
            GetCommentResponse dto = new GetCommentResponse(
                    comment.getId(),
                    comment.getUser().getName(),
                    comment.getContent()
            );
            dtos.add(dto);
        }

        return new GetOneScheduleResponse(
                schedule.getId(),
                schedule.getUser().getName(),
                schedule.getTitle(),
                schedule.getContent(),
                dtos,
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );

    }

    @Transactional
    public UpdateScheduleResponse update(@Valid Long scheduleId, UpdateScheduleRequest request, Long sessionUserId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                ()-> new NonExistentException("존재하지 않는 일정입니다.")
        );

        if (!sessionUserId.equals(schedule.getUser().getId())){
            throw new UnauthorizedUserException();
        }

        schedule.update(request.getTitle(), request.getContent());

        return new UpdateScheduleResponse(
                schedule.getId(),
                schedule.getUser().getName(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getModifiedAt()
        );
    }

    @Transactional
    public void delete(@Valid Long scheduleId, Long sessionUserId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                ()-> new NonExistentException("존재하지 않는 일정입니다.")
        );

        if (!sessionUserId.equals(schedule.getUser().getId())){
            throw new UnauthorizedUserException();
        }

        scheduleRepository.delete(schedule);
    }
}

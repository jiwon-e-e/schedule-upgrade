package com.example.schedule_upgrade.comment.service;

import com.example.schedule_upgrade.comment.dto.CreateCommentRequest;
import com.example.schedule_upgrade.comment.dto.CreateCommentResponse;
import com.example.schedule_upgrade.comment.dto.GetCommentResponse;
import com.example.schedule_upgrade.comment.entity.Comment;
import com.example.schedule_upgrade.comment.repository.CommentRepository;
import com.example.schedule_upgrade.exception.NonExistentException;
import com.example.schedule_upgrade.schedule.entity.Schedule;
import com.example.schedule_upgrade.schedule.repository.ScheduleRepository;
import com.example.schedule_upgrade.user.entity.User;
import com.example.schedule_upgrade.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    public CreateCommentResponse createComment(@Valid Long scheduleId, CreateCommentRequest request, Long sessionUserId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                ()->new NonExistentException("존재하지 않는 일정입니다.")
        );
        User user = userRepository.findById(sessionUserId).orElseThrow(
                ()->new NonExistentException("존재하지 않는 사용자입니다.")
        );

        Comment comment = new Comment(
                request.getContent(),
                schedule,
                user
        );

        Comment savedComment = commentRepository.save(comment);

        return new CreateCommentResponse(
                savedComment.getId(),
                savedComment.getUser().getName(),
                savedComment.getContent(),
                savedComment.getCreatedAt()
        );
    }

    @Transactional
    public List<GetCommentResponse> getAll(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                ()->new NonExistentException("존재하지 않는 일정입니다.")
        );

        List<Comment> commentList = commentRepository.findAllBySchedule(schedule);

        List<GetCommentResponse> dtos = new ArrayList<>();
        for (Comment comment : commentList) {
            GetCommentResponse dto = new GetCommentResponse(
                    comment.getId(),
                    comment.getUser().getName(),
                    comment.getContent()
            );
            dtos.add(dto);
        }

        return dtos;
    }
}

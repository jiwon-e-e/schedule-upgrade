package com.example.schedule_upgrade.comment.service;

import com.example.schedule_upgrade.comment.dto.CreateCommentRequest;
import com.example.schedule_upgrade.comment.dto.CreateCommentResponse;
import com.example.schedule_upgrade.comment.dto.GetCommentResponse;
import com.example.schedule_upgrade.comment.entity.Comment;
import com.example.schedule_upgrade.comment.repository.CommentRepository;
import com.example.schedule_upgrade.exception2.ErrorCode;
import com.example.schedule_upgrade.exception2.ServiceException;
import com.example.schedule_upgrade.schedule.entity.Schedule;
import com.example.schedule_upgrade.schedule.repository.ScheduleRepository;
import com.example.schedule_upgrade.schedule.service.ScheduleService;
import com.example.schedule_upgrade.user.entity.User;
import com.example.schedule_upgrade.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                ()->new ServiceException(ErrorCode.SCHEDULE_NOT_FOUND)
        );

        User user = userRepository.findById(sessionUserId).orElseThrow(
                ()->new ServiceException(ErrorCode.BEFORE_LOGIN)
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
    public List<GetCommentResponse> getAll(Long scheduleId, int page, int size) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                ()->new ServiceException(ErrorCode.SCHEDULE_NOT_FOUND)
        );

        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentList = commentRepository.findAllBySchedule_Id(schedule.getId(), pageable);

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

    @Transactional
    public void delete(Long scheduleId, Long commentId, Long sessionUserId) {
        // 만약 일정 삭제시 댓글도 다 삭제되면 이 부분 없어도 됨
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                ()->new ServiceException(ErrorCode.SCHEDULE_NOT_FOUND)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ServiceException(ErrorCode.COMMENT_NOT_FOUND)
        );
        if (!sessionUserId.equals(comment.getUser().getId())){
            throw new ServiceException(ErrorCode.WRITER_MISMATCH);
        }

        commentRepository.delete(comment);
    }
}

package com.example.schedule_upgrade.comment.controller;

import com.example.schedule_upgrade.comment.dto.CreateCommentRequest;
import com.example.schedule_upgrade.comment.dto.CreateCommentResponse;
import com.example.schedule_upgrade.comment.dto.GetCommentResponse;
import com.example.schedule_upgrade.comment.service.CommentService;
import com.example.schedule_upgrade.exception2.ErrorCode;
import com.example.schedule_upgrade.exception2.ServiceException;
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
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    ResponseEntity<CreateCommentResponse> create(
            @Valid @PathVariable Long scheduleId,
            @Valid @RequestBody CreateCommentRequest request,
            HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new ServiceException(ErrorCode.BEFORE_LOGIN);
        }

        CreateCommentResponse response = commentService.createComment(scheduleId, request, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    ResponseEntity<List<GetCommentResponse>> getAll(
            @PathVariable Long scheduleId,
            @Valid @RequestParam(defaultValue = "0") int page,
            @Valid @RequestParam(defaultValue = "5") int size
    ){
        List<GetCommentResponse> response = commentService.getAll(scheduleId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/schedules/{scheduleId}/comments/{commentId}")
    ResponseEntity<Void> delete(@PathVariable Long scheduleId,
                                @PathVariable Long commentId, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new ServiceException(ErrorCode.BEFORE_LOGIN);
        }

        commentService.delete(scheduleId, commentId, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

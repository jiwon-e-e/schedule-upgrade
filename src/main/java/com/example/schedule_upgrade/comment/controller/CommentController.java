package com.example.schedule_upgrade.comment.controller;

import com.example.schedule_upgrade.comment.dto.CreateCommentRequest;
import com.example.schedule_upgrade.comment.dto.CreateCommentResponse;
import com.example.schedule_upgrade.comment.service.CommentService;
import com.example.schedule_upgrade.exception.BeforeLoginUserException;
import com.example.schedule_upgrade.user.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    ResponseEntity<CreateCommentResponse> create(
            @Valid @PathVariable Long scheduleId, @RequestBody CreateCommentRequest request, HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new BeforeLoginUserException();
        }

        CreateCommentResponse response = commentService.createComment(scheduleId, request, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

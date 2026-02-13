package com.example.schedule_upgrade.comment.controller;

import com.example.schedule_upgrade.comment.dto.CreateCommentRequest;
import com.example.schedule_upgrade.comment.dto.CreateCommentResponse;
import com.example.schedule_upgrade.comment.dto.GetCommentResponse;
import com.example.schedule_upgrade.comment.service.CommentService;
import com.example.schedule_upgrade.global.exception.ErrorCode;
import com.example.schedule_upgrade.global.exception.ServiceException;
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

    // 대부분 요청에 HttpSession 을 받아와서 sessionUser 를 확인하는 과정이 추가되었습니다.
    // 로그인이 꼭 필요한 요청은 sessionUser 가 null 이라면 Exception 으로 처리됩니다.

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

    // GET 요청에 page의 크기와 번호를 지정할 수 있습니다.
    // size-> 한 페이지당 몇 개의 정보가 조회되는지 지정
    // page-> 몇 번째 페이지를 조회할건지 지정

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

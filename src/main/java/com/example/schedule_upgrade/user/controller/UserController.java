package com.example.schedule_upgrade.user.controller;

import com.example.schedule_upgrade.global.exception.ErrorCode;
import com.example.schedule_upgrade.global.exception.ServiceException;
import com.example.schedule_upgrade.user.dto.*;
import com.example.schedule_upgrade.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request){
        SignupResponse response = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    ResponseEntity<String> login(
            @Valid @RequestBody LoginRequest request,
            HttpSession session
    ){
        SessionUser sessionUser = userService.login(request);
        session.setAttribute("loginUser", sessionUser);
        session.setMaxInactiveInterval(120);
        return ResponseEntity.status(HttpStatus.OK).body("로그인에 성공했습니다.");
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAll(
            @Valid @RequestParam(defaultValue = "0") int page,
            @Valid @RequestParam(defaultValue = "5") int size
    ){
        List<GetUserResponse> response = userService.findAll(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UpdateUserResponse> update(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request,
            HttpSession session
    ){
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new ServiceException(ErrorCode.BEFORE_LOGIN);
        }

        UpdateUserResponse response = userService.update(userId, sessionUser.getId(), request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new ServiceException(ErrorCode.BEFORE_LOGIN);
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 되었습니다.");
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long userId, HttpSession session
    ){
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new ServiceException(ErrorCode.BEFORE_LOGIN);
        }

        userService.delete(userId, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

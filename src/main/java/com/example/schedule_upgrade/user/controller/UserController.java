package com.example.schedule_upgrade.user.controller;

import com.example.schedule_upgrade.exception.BeforeLoginUserException;
import com.example.schedule_upgrade.user.dto.*;
import com.example.schedule_upgrade.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
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
    public ResponseEntity<List<GetUserResponse>> getAll(){
        List<GetUserResponse> response = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session){
        SessionUser sessionUser = (SessionUser) session.getAttribute("loginUser");
        if( sessionUser == null){
            throw new BeforeLoginUserException();
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK).body("로그아웃 되었습니다.");
    }

}

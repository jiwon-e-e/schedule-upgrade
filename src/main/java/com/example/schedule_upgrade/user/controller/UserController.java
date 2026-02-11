package com.example.schedule_upgrade.user.controller;

import com.example.schedule_upgrade.user.dto.*;
import com.example.schedule_upgrade.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request){
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
        return ResponseEntity.status(HttpStatus.OK).body(sessionUser.getName() +"님, 로그인에 성공했습니다.");
    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserResponse>> getAll(){
        List<GetUserResponse> response = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

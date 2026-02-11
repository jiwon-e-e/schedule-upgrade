package com.example.schedule_upgrade.user.service;

import com.example.schedule_upgrade.exception.UnauthorizedUserException;
import com.example.schedule_upgrade.exception.WrongPwException;
import com.example.schedule_upgrade.user.dto.*;
import com.example.schedule_upgrade.user.entity.User;
import com.example.schedule_upgrade.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        User user = new User(request.getName(), request.getEmail(), request.getPw());
        User savedUser = userRepository.save(user);

        return new SignupResponse(savedUser.getName(), savedUser.getEmail());
    }

    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UnauthorizedUserException()
        );

        if (!user.getPw().equals(request.getPw())){
            throw new WrongPwException();
        }

        return new SessionUser(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    @Transactional
    public List<GetUserResponse> findAll() {
        List<User> users = userRepository.findAll();
        List<GetUserResponse> dtos = new ArrayList<>();

        for (User user : users) {
            GetUserResponse dto = new GetUserResponse(
                    user.getId(),
                    user.getName(),
                    user.getEmail()
            );
            dtos.add(dto);
        }
        return dtos;
    }
}

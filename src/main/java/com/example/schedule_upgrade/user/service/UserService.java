package com.example.schedule_upgrade.user.service;

import com.example.schedule_upgrade.config.PasswordEncoder;
import com.example.schedule_upgrade.exception2.ErrorCode;
import com.example.schedule_upgrade.exception2.ServiceException;
import com.example.schedule_upgrade.user.dto.*;
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

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){
            throw new ServiceException(ErrorCode.DUPLICATE_EMAIL);
        }


        //암호화 하고 db에 저장
        String encodePw = passwordEncoder.encode(request.getPw());

        System.out.println("사용자 입력 pw: "+request.getPw());
        System.out.println("암호화 이후 pw: "+encodePw);

        User user = new User(request.getName(), request.getEmail(), encodePw);
        User savedUser = userRepository.save(user);

        return new SignupResponse(savedUser.getName(), savedUser.getEmail());
    }

    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ServiceException(ErrorCode.USER_NOT_FOUND)
        );

        // 로그인 할 때 비교 다시하기
//        if (!user.getPw().equals(request.getPw())){
//            throw new WrongPwException();
//        }
        if(!passwordEncoder.matches(request.getPw(), user.getPw())){
            throw new ServiceException(ErrorCode.WRONG_PW);
        }

        return new SessionUser(
                user.getId()
        );
    }

    @Transactional
    public List<GetUserResponse> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
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

    @Transactional
    public UpdateUserResponse update(Long userId, Long sessionUserId, @Valid UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ServiceException(ErrorCode.USER_NOT_FOUND)
        );

        if(!sessionUserId.equals(userId)){
            throw new ServiceException(ErrorCode.USER_MISMATCH);
        }

        user.update(request.getName());

        return new UpdateUserResponse(user.getName());
    }

    @Transactional
    public void delete(Long userId, Long sessionUserId){
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ServiceException(ErrorCode.USER_NOT_FOUND)
        );

        if(!sessionUserId.equals(userId)){
            throw new ServiceException(ErrorCode.USER_MISMATCH);
        }

        userRepository.delete(user);
    }
}

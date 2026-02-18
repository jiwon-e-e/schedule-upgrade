package com.example.schedule_upgrade.user.service;

import com.example.schedule_upgrade.global.config.PasswordEncoder;
import com.example.schedule_upgrade.global.exception.ErrorCode;
import com.example.schedule_upgrade.global.exception.ServiceException;
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

        // 이메일이 이미 존재하는지 확인하고 있다면 중복 이메일 Exception 처리
        if (userRepository.existsByEmail(request.getEmail())){
            throw new ServiceException(ErrorCode.DUPLICATE_EMAIL);
        }


        //암호화 하고 db에 저장
        String encodePw = passwordEncoder.encode(request.getPw());

//        System.out.println("사용자 입력 pw: "+request.getPw());
//        System.out.println("암호화 이후 pw: "+encodePw);

        User user = new User(request.getName(), request.getEmail(), encodePw);
        User savedUser = userRepository.save(user);

        return new SignupResponse(savedUser.getName(), savedUser.getEmail());
    }

    @Transactional(readOnly = true)
    public SessionUser login(LoginRequest request) {

        // 로그인을 시도하는 이메일이 존재하지 않는다면
        // 존재하지 않는 사용자 Exception 처리
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ServiceException(ErrorCode.USER_NOT_FOUND)
        );

        // 로그인 할 때 비교 다시하기
//        if (!user.getPw().equals(request.getPw())){
//            throw new WrongPwException();
//        }
        // 암호화 한 값과 일치하는지 확인
        if(!passwordEncoder.matches(request.getPw(), user.getPw())){
            throw new ServiceException(ErrorCode.WRONG_PW);
        }

        // SessionUser return
        return new SessionUser(
                user.getId()
        );
    }

    @Transactional
    public List<GetUserResponse> findAll(int page, int size) {

        // Query parameter 로 받은 page 와 size 를 pageable 의 인수로 사용
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
    public UpdateUserResponse update(Long userId, Long sessionUserId, UpdateUserRequest request) {
        // PathVariable 로 받은 User ID 로 user 조회
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ServiceException(ErrorCode.USER_NOT_FOUND)
        );

        // 수정하려는 유저가 로그인 된 유저인지 확인
        // 아니라면 유저 mismatch Exception 처리
        if(!sessionUserId.equals(userId)){
            throw new ServiceException(ErrorCode.USER_MISMATCH);
        }

        user.update(request.getName());

        return new UpdateUserResponse(user.getName());
    }

    @Transactional
    public void delete(Long userId, Long sessionUserId){
        // PathVariable 로 받은 User ID 로 user 조회
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ServiceException(ErrorCode.USER_NOT_FOUND)
        );

        // 수정하려는 유저가 로그인 된 유저인지 확인
        // 아니라면 유저 mismatch Exception 처리
        if(!sessionUserId.equals(userId)){
            throw new ServiceException(ErrorCode.USER_MISMATCH);
        }

        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public GetUserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()->new ServiceException(ErrorCode.USER_NOT_FOUND)
        );

        return new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // Optional 처리를 끝낸 User return
    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(
                ()->new ServiceException(ErrorCode.USER_NOT_FOUND)
        );
    }
}

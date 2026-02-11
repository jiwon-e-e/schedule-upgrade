package com.example.schedule_upgrade;

import com.example.schedule_upgrade.exception.BeforeLoginUserException;
import com.example.schedule_upgrade.user.dto.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        HttpSession session = request.getSession(false);
        if(session == null){
            throw new BeforeLoginUserException();
        }

        SessionUser user = (SessionUser) session.getAttribute("loginUser");
        if (user == null) {
            throw new BeforeLoginUserException();
        }
        return true;
    }
}

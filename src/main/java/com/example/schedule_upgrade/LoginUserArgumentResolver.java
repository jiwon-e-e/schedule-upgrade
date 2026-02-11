package com.example.schedule_upgrade;

import com.example.schedule_upgrade.exception.BeforeLoginUserException;
import com.example.schedule_upgrade.user.dto.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    //파라미터를 처리할지 말지 결정
    @Override
    public boolean supportsParameter(MethodParameter parameter){
        //내가 만든 @LoginUser annotation 이 붙은건지
        boolean hasAnnotation = parameter.hasMethodAnnotation(LoginUser.class);
        // SessionUser 라는 타입이 들어온게 맞는지 확인
        boolean hasSessionUser = SessionUser.class.isAssignableFrom(parameter.getParameterType());

        //둘다 맞으면 true 반환
        if (hasAnnotation&&hasSessionUser) return true;
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory){
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        //기존 세션 가져오기
        HttpSession session = request.getSession(false);

        if (session == null){
            throw new BeforeLoginUserException();
        }

        // login user 꺼내서 반환
        return session.getAttribute("loginUser");
    }

}

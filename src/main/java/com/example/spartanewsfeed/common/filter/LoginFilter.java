package com.example.spartanewsfeed.common.filter;

import com.example.spartanewsfeed.common.exception.GlobalException;
import com.example.spartanewsfeed.common.exception.InvalidCredentialException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    // 필터가 적용되지 않는 주소들(로그인 없이 접근 가능한 주소들)
    private static final String[] WHITE_LIST = {"/login", "/users/signup"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException{
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse  httpResponse = (HttpServletResponse) response;
        String requestURI = httpRequest.getRequestURI();

        if (!isWhiteList(requestURI)) {
            HttpSession session = httpRequest.getSession(false);
            if(session == null || session.getAttribute("sessionKey") == null){
                throw new GlobalException(HttpStatus.UNAUTHORIZED, "로그인 해주세요.");
            }
        }
        chain.doFilter(request, response);
    }
    private boolean isWhiteList(String requestURI){
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}

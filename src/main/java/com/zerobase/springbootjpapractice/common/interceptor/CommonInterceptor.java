package com.zerobase.springbootjpapractice.common.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.zerobase.springbootjpapractice.common.exception.AuthFailException;
import com.zerobase.springbootjpapractice.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class CommonInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("CommonInterceptor preHandle start");
        log.info(request.getRequestURI());
        log.info(request.getMethod());

        if(!validJWT(request)){
            throw new AuthFailException("인증정보가 정확하지 않습니다.");
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private boolean validJWT(HttpServletRequest request) {
        String token = request.getHeader("F-TOKEN");
        String email = "";
        try{
            email = JWTUtils.getIssuer(token);
        }catch (JWTDecodeException e){
            return false;
        }
        return true;
    }

}

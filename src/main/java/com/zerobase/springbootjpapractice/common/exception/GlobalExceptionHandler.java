package com.zerobase.springbootjpapractice.common.exception;

import com.zerobase.springbootjpapractice.common.model.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthFailException.class)
    public ResponseEntity<?> AuthFailException(AuthFailException e) {
        return ResponseResult.fail("[인증실패] " + e.getMessage());
    }
}

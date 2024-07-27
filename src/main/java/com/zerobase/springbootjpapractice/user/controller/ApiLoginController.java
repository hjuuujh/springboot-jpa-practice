package com.zerobase.springbootjpapractice.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.zerobase.springbootjpapractice.board.entity.Board;
import com.zerobase.springbootjpapractice.board.entity.BoardComment;
import com.zerobase.springbootjpapractice.board.model.ServiceResult;
import com.zerobase.springbootjpapractice.board.service.BoardService;
import com.zerobase.springbootjpapractice.common.exception.BizException;
import com.zerobase.springbootjpapractice.common.model.ResponseResult;
import com.zerobase.springbootjpapractice.notice.entity.Notice;
import com.zerobase.springbootjpapractice.notice.entity.NoticeLike;
import com.zerobase.springbootjpapractice.notice.model.NoticeResponse;
import com.zerobase.springbootjpapractice.notice.model.ResponseError;
import com.zerobase.springbootjpapractice.notice.repository.NoticeLikeRepository;
import com.zerobase.springbootjpapractice.notice.repository.NoticeRepository;
import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.exception.ExistEmailException;
import com.zerobase.springbootjpapractice.user.exception.PasswordNotMatchException;
import com.zerobase.springbootjpapractice.user.exception.UserNotFoundException;
import com.zerobase.springbootjpapractice.user.model.*;
import com.zerobase.springbootjpapractice.user.repository.UserRepository;
import com.zerobase.springbootjpapractice.user.service.PointService;
import com.zerobase.springbootjpapractice.user.service.UserService;
import com.zerobase.springbootjpapractice.util.JWTUtils;
import com.zerobase.springbootjpapractice.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiLoginController {

    private final UserService userService;

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLogin userLogin, Errors errors){
        if(errors.hasErrors()){
            return ResponseResult.fail("입력값이 정확하지 않습니다.", ResponseError.of(errors.getAllErrors()));
        }

        User user;
        try{
            user = userService.login(userLogin);
        }catch (BizException e){
            log.info("로그인 에러: "+e.getMessage());
            return ResponseResult.fail(e.getMessage());
        }

        UserLoginToken token = JWTUtils.createToken(user);
        if(token == null){
            return ResponseResult.fail("JWT 생성실패");
        }

        return ResponseResult.success(token);
    }
}

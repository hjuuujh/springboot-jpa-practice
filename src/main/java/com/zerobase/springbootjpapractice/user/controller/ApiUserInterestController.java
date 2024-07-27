package com.zerobase.springbootjpapractice.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.zerobase.springbootjpapractice.board.model.ServiceResult;
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
import com.zerobase.springbootjpapractice.user.service.UserService;
import com.zerobase.springbootjpapractice.util.JWTUtils;
import com.zerobase.springbootjpapractice.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class ApiUserInterestController {

    private final UserService userService;

    @PutMapping("/api/user/{id}/interest")
    public ResponseEntity<?> interestUser(@PathVariable Long id
            , @RequestHeader("F-TOKEN") String token) {
        String email;
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = userService.addInterestUser(email, id);

        return ResponseResult.success();
    }

    @DeleteMapping("/api/user/{id}/interest")
    public ResponseEntity<?> deleteInterestUser(@PathVariable Long id
            , @RequestHeader("F-TOKEN") String token) {
        String email;
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = userService.removeInterestUser(email, id);

        return ResponseResult.success();
    }
}

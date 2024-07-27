package com.zerobase.springbootjpapractice.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.zerobase.springbootjpapractice.board.entity.Board;
import com.zerobase.springbootjpapractice.board.entity.BoardComment;
import com.zerobase.springbootjpapractice.board.model.ServiceResult;
import com.zerobase.springbootjpapractice.board.service.BoardService;
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
public class ApiUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeLikeRepository noticeLikeRepository;
    private final BoardService boardService;
    private final PointService pointService;

    @PostMapping("/api/user")
    public ResponseEntity<?> addUserValidation(@RequestBody @Valid UserInput input, Errors errors) {
        List<ResponseError> responseErrors = new ArrayList<>();

        if (errors.hasErrors()) {

            errors.getAllErrors().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/api/user2")
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInput input, Errors errors) {
        List<ResponseError> responseErrors = new ArrayList<>();

        if (errors.hasErrors()) {

            errors.getAllErrors().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .email(input.getEmail())
                .userName(input.getUserName())
                .password(input.getPassword())
                .phone(input.getPhone())
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundExceptionHandler(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @RequestBody @Valid UserUpdate input, Errors errors) {
        List<ResponseError> responseErrors = new ArrayList<>();

        if (errors.hasErrors()) {

            errors.getAllErrors().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        user.setPhone(input.getPhone());
        user.setUpdateDate(LocalDateTime.now());
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
        UserResponse res = UserResponse.of(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/api/user/{id}/notice")
    public List<NoticeResponse> userNotice(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        List<Notice> noticeList = noticeRepository.findByUser(user);
        List<NoticeResponse> res = new ArrayList<>();
        noticeList.forEach(notice -> {
            res.add(NoticeResponse.of(notice));
        });

        return res;
    }

    @ExceptionHandler(value = {ExistEmailException.class, PasswordNotMatchException.class})
    public ResponseEntity<?> ExistEmailExceptionHandler(RuntimeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }


    @PostMapping("/api/user3")
    public ResponseEntity<?> addUserExitsEmailException(@RequestBody @Valid UserInput input, Errors errors) {
        List<ResponseError> responseErrors = new ArrayList<>();

        if (errors.hasErrors()) {

            errors.getAllErrors().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        boolean exists = userRepository.existsByEmail(input.getEmail());
        if (exists) {
            throw new ExistEmailException("이미 가입된 이메일이 존재합니다.");
        }

        User user = User.builder()
                .email(input.getEmail())
                .userName(input.getUserName())
                .password(input.getPassword())
                .phone(input.getPhone())
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/api/user/{id}/password")
    public ResponseEntity<?> updateUserPassword(@PathVariable Long id, @RequestBody UserInputPassword input, Errors errors) {
        List<ResponseError> responseErrors = new ArrayList<>();

        if (errors.hasErrors()) {

            errors.getAllErrors().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByIdAndPassword(id, input.getPassword())
                .orElseThrow(() -> new PasswordNotMatchException("비밀번호가 일치하지 않습니다."));
        user.setPassword(input.getPassword());
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/api/user4")
    public ResponseEntity<?> addUserWithEncrypt(@RequestBody @Valid UserInput input, Errors errors) {
        List<ResponseError> responseErrors = new ArrayList<>();

        if (errors.hasErrors()) {

            errors.getAllErrors().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        // 암호화
        String encryptPassword = getEncryptPassword(input.getPassword());

        User user = User.builder()
                .email(input.getEmail())
                .userName(input.getUserName())
                .password(encryptPassword)
                .phone(input.getPhone())
                .regDate(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private static String getEncryptPassword(String input) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(input);
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        try {
            userRepository.delete(user);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("제약조건에 문제가 발생했습니다.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("회원탈퇴중 문제가 발생하였습니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> getUser(@RequestBody UserInputFind input) {
        User user = userRepository.findByUserNameAndPhone(input.getUserName(), input.getPhone())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        UserResponse res = UserResponse.of(user);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    private String getResetPassword() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }

    @GetMapping("/api/user/{id}/password/reset")
    public ResponseEntity<?> resetUserPassword(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        //비밀번호 초기화
        String resetPassword = getResetPassword();
        String encryptPassword = getEncryptPassword(resetPassword);
        user.setPassword(encryptPassword);
        userRepository.save(user);

        String message = String.format("[%s]의 임시 비밀번호가 [%s]로 초기화"
                , user.getUserName()
                , resetPassword);
        sendSMS(message);
        return ResponseEntity.ok().build();
    }

    private void sendSMS(String message) {
        System.out.println("문자메세지전송");
        System.out.println(message);
    }

    @GetMapping("/api/user/{id}/notice/like")
    public List<NoticeLike> likeNotice(@PathVariable Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        List<NoticeLike> noticeLikeList = noticeLikeRepository.findByUser(user);
        return noticeLikeList;
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<?> createTokenLogin(@RequestBody UserLogin userLogin, Errors errors) {
        List<ResponseError> responseErrors = new ArrayList<>();

        if (errors.hasErrors()) {

            errors.getAllErrors().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        if (PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException("패스워드가 일치하지 않습니다.");
        }

        return ResponseEntity.ok().body(user);

    }

    @PostMapping("/api/user/login2")
    public ResponseEntity<?> createToken(@RequestBody UserLogin userLogin, Errors errors) {
        List<ResponseError> responseErrors = new ArrayList<>();

        if (errors.hasErrors()) {

            errors.getAllErrors().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });
            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        if (PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException("패스워드가 일치하지 않습니다.");
        }

        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
        Date expiredDate = java.sql.Timestamp.valueOf(expiredDateTime);
        // 토큰발행
        String token = JWT.create()
                .withExpiresAt(expiredDate)
                .withClaim("user_id", user.getId())
                .withSubject(user.getUserName())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512("zerobase".getBytes()));


        return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());

    }

    @PatchMapping("/api/user/login")
    public ResponseEntity<?> replaceToken(HttpServletRequest request) {
        String token = request.getHeader("F-TOKEN");
        String email = "";
        try {
            email = JWT.require(Algorithm.HMAC512("zerobase".getBytes()))
                    .build()
                    .verify(token)
                    .getIssuer();
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));
        LocalDateTime expiredDateTime = LocalDateTime.now().plusMonths(1);
        Date expiredDate = java.sql.Timestamp.valueOf(expiredDateTime);
        // 토큰발행
        String newToken = JWT.create()
                .withExpiresAt(expiredDate)
                .withClaim("user_id", user.getId())
                .withSubject(user.getUserName())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512("zerobase".getBytes()));

        return ResponseEntity.ok().body(UserLoginToken.builder().token(newToken).build());

    }

    @DeleteMapping("/api/user/login")
    public ResponseEntity<?> removeToken(@RequestHeader("F-TOKEN") String token) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/user/board/post")
    public ResponseEntity<?> myPost(@RequestHeader("F-TOKEN") String token) {

        String email;
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        List<Board> list = boardService.postList(email);

        return ResponseResult.success(list);
    }

    @GetMapping("/api/user/board/comment")
    public ResponseEntity<?> myComments(@RequestHeader("F-TOKEN") String token) {
        String email;
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        List<BoardComment> list = boardService.commentList(email);

        return ResponseResult.success(list);
    }

    @PostMapping("/api/user/point")
    public ResponseEntity<?> userPoint(@RequestHeader("F-TOKEN") String token
            , @RequestBody UserPointInput input) {
        String email;
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = pointService.addPoint(email, input);

        return ResponseResult.success(result);
    }
}

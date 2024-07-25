package com.zerobase.springbootjpapractice.user.controller;

import com.zerobase.springbootjpapractice.notice.repository.NoticeRepository;
import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.entity.UserLoginHistory;
import com.zerobase.springbootjpapractice.user.model.*;
import com.zerobase.springbootjpapractice.user.repository.UserLoginHistoryRepository;
import com.zerobase.springbootjpapractice.user.repository.UserRepository;
import com.zerobase.springbootjpapractice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ApiAdminUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserLoginHistoryRepository userLoginHistoryRepository;
    private final UserService userService;

    @GetMapping("/api/admin/user")
    public ResponseMessage userList() {
        List<User> users = userRepository.findAll();
        long total = userRepository.count();
        return ResponseMessage.builder()
                .body(users)
//                .count(total)
                .build();
    }

    @GetMapping("/api/admin/user/{id}")
    public ResponseEntity<?> userDetail(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(ResponseMessage.success(user), HttpStatus.OK);
    }

    @GetMapping("/api/admin/user/search")
    public ResponseEntity<?> findUser(@RequestBody UserSearch userSearch) {

        List<User> users = userRepository.findByEmailContainsOrPhoneContainsOrUserNameContains(
                userSearch.getEmail(), userSearch.getPhone(), userSearch.getUserName()
        );

        return ResponseEntity.ok().body(ResponseMessage.success(Optional.of(users)));
    }

    @PatchMapping("/api/admin/user/{id}/status")
    public ResponseEntity<?> userStatus(@PathVariable Long id, @RequestBody UserStatusInput userStatusInput) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();
        user.setStatus(userStatusInput.getStatus());
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/admin/user")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();

        if(!noticeRepository.findByUser(user).isEmpty()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 작성한 공지사항이 있습니다."), HttpStatus.BAD_REQUEST);
        }

        userRepository.delete(user);
        return ResponseEntity.ok().build();

    }

    @GetMapping("/api/admin/user/login/history")
    public ResponseEntity<?> userLoginHistory() {
        List<UserLoginHistory> userLoginHistories = userLoginHistoryRepository.findAll();
        return ResponseEntity.ok().body(userLoginHistories);
    }

    @PatchMapping("/api/admin/user/{id}/lock")
    public ResponseEntity<?> userLock(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();

        if(user.isLockYn()){
            return new ResponseEntity<>(ResponseMessage.fail("이미 접속이 제한된 사용자입니다."), HttpStatus.BAD_REQUEST);

        }

        user.setLockYn(true);
        userRepository.save(user);
        return ResponseEntity.ok().body(ResponseMessage.success());


    }

    @PatchMapping("/api/admin/user/{id}/unlock")
    public ResponseEntity<?> userUnLock(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 존재하지 않습니다."), HttpStatus.BAD_REQUEST);
        }
        User user = optionalUser.get();

        if(!user.isLockYn()){
            return new ResponseEntity<>(ResponseMessage.fail("이미 접속 제한이 해제된 사용자입니다."), HttpStatus.BAD_REQUEST);

        }

        user.setLockYn(false);
        userRepository.save(user);
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/admin/user/status/count")
    public ResponseEntity<?> userStatusCount() {
        UserSummary userSummary = userService.getUserStatusCount();


        return ResponseEntity.ok().body(ResponseMessage.success(Optional.of(userSummary)));
    }

    @GetMapping("/api/admin/user/today")
    public ResponseEntity<?> todayUser() {
        List<User> users = userService.getTodayUsers();
        return ResponseEntity.ok().body(ResponseMessage.success(Optional.of(users)));

    }

    @GetMapping("/api/admin/user/notice/count")
    public ResponseEntity<?> userNoticeCount() {
        List<UserNoticeCount> userNoticeCounts = userService.getUserNoticeCount();
        return ResponseEntity.ok().body(ResponseMessage.success(Optional.of(userNoticeCounts)));
    }

    @GetMapping("/api/admin/user/notice/count/like")
    public ResponseEntity<?> userLogCount() {
        List<UserLogCount> userLogCounts = userService.getUserLogCount();
        return ResponseEntity.ok().body(ResponseMessage.success(Optional.of(userLogCounts)));
    }

    @GetMapping("/api/admin/user/like/best")
    public ResponseEntity<?> bestLikeCount() {
        List<UserLogCount> userLogCounts = userService.getUserLikeBest();
        return ResponseEntity.ok().body(ResponseMessage.success(Optional.of(userLogCounts)));
    }



}

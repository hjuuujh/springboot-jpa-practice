package com.zerobase.springbootjpapractice.user.service;

import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.model.UserLogCount;
import com.zerobase.springbootjpapractice.user.model.UserNoticeCount;
import com.zerobase.springbootjpapractice.user.model.UserSummary;

import java.util.List;

public interface UserService {
    UserSummary getUserStatusCount();

    List<User> getTodayUsers();

    List<UserNoticeCount> getUserNoticeCount();

    List<UserLogCount> getUserLogCount();

    List<UserLogCount> getUserLikeBest();
}

package com.zerobase.springbootjpapractice.user.service;

import com.zerobase.springbootjpapractice.board.model.ServiceResult;
import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.model.*;

import java.util.List;

public interface UserService {
    UserSummary getUserStatusCount();

    List<User> getTodayUsers();

    List<UserNoticeCount> getUserNoticeCount();

    List<UserLogCount> getUserLogCount();

    List<UserLogCount> getUserLikeBest();

    ServiceResult addInterestUser(String email, Long id);

    ServiceResult removeInterestUser(String email, Long id);

    User login(UserLogin userLogin);

    ServiceResult addUser(UserInput input);

    ServiceResult resetPassword(UserPasswordResetInput input);

    void sendServiceNotice();

}

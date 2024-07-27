package com.zerobase.springbootjpapractice.user.service;

import com.zerobase.springbootjpapractice.board.model.ServiceResult;
import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.model.*;

import java.util.List;

public interface PointService {
    ServiceResult addPoint(String email, UserPointInput userPointInput);
}

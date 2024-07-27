package com.zerobase.springbootjpapractice.user.service;

import com.zerobase.springbootjpapractice.board.model.ServiceResult;
import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.entity.UserPoint;
import com.zerobase.springbootjpapractice.user.model.UserPointInput;
import com.zerobase.springbootjpapractice.user.model.UserPointType;
import com.zerobase.springbootjpapractice.user.repository.UserPointRepository;
import com.zerobase.springbootjpapractice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
    private final UserRepository userRepository;
    private final UserPointRepository userPointRepository;


    @Override
    public ServiceResult addPoint(String email, UserPointInput userPointInput) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        UserPoint userPoint = UserPoint.builder()
                .user(user)
                .userPointType(userPointInput.getUserPointType())
                .point(userPointInput.getUserPointType().getValue())
                .build();

        userPointRepository.save(userPoint);

        return ServiceResult.success();
    }
}

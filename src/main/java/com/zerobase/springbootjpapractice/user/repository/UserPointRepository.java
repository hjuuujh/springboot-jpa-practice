package com.zerobase.springbootjpapractice.user.repository;

import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.entity.UserInterest;
import com.zerobase.springbootjpapractice.user.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPointRepository extends JpaRepository<UserPoint, Long> {
}

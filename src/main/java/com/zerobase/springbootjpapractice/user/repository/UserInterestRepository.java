package com.zerobase.springbootjpapractice.user.repository;

import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.entity.UserInterest;
import com.zerobase.springbootjpapractice.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserInterestRepository extends JpaRepository<UserInterest, Long> {
    long countByUserAndAndInterestUser(User user, User interestUser);
}

package com.zerobase.springbootjpapractice.user.repository;

import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.model.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByIdAndPassword(Long id, String password);
    Optional<User> findByUserNameAndPhone(String userName, String phone);

    Optional<User> findByEmail(String email);

    List<User> findByEmailContainsOrPhoneContainsOrUserNameContains(String email, String phone, String username);

    long countByStatus(UserStatus status);

    // Query에 * 불가능 alias이용
    @Query("select u from User u where u.regDate between :startDate and :endDate")
    List<User> findByToday(LocalDateTime startDate, LocalDateTime endDate);
}

package com.zerobase.springbootjpapractice.notice.repository;

import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    List<Notice> findByIdIn(List<Long> idList);

    // 제목동일, 내용동일, 등록시간이 체크시간보다 크다
    List<Notice> findByTitleAndContentsAndRegDateIsGreaterThanEqual(String title, String contents, LocalDateTime regDate);
    int countByTitleAndContentsAndRegDateIsGreaterThanEqual(String title, String contents, LocalDateTime regDate);

    List<Notice> findByUser(User user);
}

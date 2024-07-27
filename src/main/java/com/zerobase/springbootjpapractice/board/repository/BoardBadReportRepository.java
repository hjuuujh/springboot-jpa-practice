package com.zerobase.springbootjpapractice.board.repository;

import com.zerobase.springbootjpapractice.board.entity.Board;
import com.zerobase.springbootjpapractice.board.entity.BoardBadReport;
import com.zerobase.springbootjpapractice.board.entity.BoardLike;
import com.zerobase.springbootjpapractice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardBadReportRepository extends JpaRepository<BoardBadReport, Long> {
}

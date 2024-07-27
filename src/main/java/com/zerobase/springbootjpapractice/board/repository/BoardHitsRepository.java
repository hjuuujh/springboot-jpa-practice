package com.zerobase.springbootjpapractice.board.repository;

import com.zerobase.springbootjpapractice.board.entity.Board;
import com.zerobase.springbootjpapractice.board.entity.BoardHits;
import com.zerobase.springbootjpapractice.board.entity.BoardType;
import com.zerobase.springbootjpapractice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardHitsRepository extends JpaRepository<BoardHits, Long> {
    long countByBoardAndUser(Board board, User user);
}

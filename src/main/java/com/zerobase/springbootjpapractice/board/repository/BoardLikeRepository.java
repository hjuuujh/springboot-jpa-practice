package com.zerobase.springbootjpapractice.board.repository;

import com.zerobase.springbootjpapractice.board.entity.Board;
import com.zerobase.springbootjpapractice.board.entity.BoardHits;
import com.zerobase.springbootjpapractice.board.entity.BoardLike;
import com.zerobase.springbootjpapractice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    long countByBoardAndUser(Board board, User user);
    Optional<BoardLike> findByBoardAndUser(Board board, User user);
}

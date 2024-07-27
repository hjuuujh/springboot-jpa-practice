package com.zerobase.springbootjpapractice.board.repository;

import com.zerobase.springbootjpapractice.board.entity.BoardBadReport;
import com.zerobase.springbootjpapractice.board.entity.BoardScrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardScarpRepository extends JpaRepository<BoardScrap, Long> {
}

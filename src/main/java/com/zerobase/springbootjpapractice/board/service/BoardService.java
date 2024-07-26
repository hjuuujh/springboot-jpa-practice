package com.zerobase.springbootjpapractice.board.service;

import com.zerobase.springbootjpapractice.board.entity.BoardType;
import com.zerobase.springbootjpapractice.board.model.BoardTypeCount;
import com.zerobase.springbootjpapractice.board.model.BoardTypeInput;
import com.zerobase.springbootjpapractice.board.model.BoardTypeUsing;
import com.zerobase.springbootjpapractice.board.model.ServiceResult;

import java.util.List;

public interface BoardService {

    ServiceResult addBoard(BoardTypeInput input);

    ServiceResult updateBoard(long id, BoardTypeInput input);

    ServiceResult deleteBoard(Long id);

    List<BoardType> getAllBoardType();

    ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing);

    List<BoardTypeCount> getBoardTypeCount();

    ServiceResult setBoardTop(Long id);
}

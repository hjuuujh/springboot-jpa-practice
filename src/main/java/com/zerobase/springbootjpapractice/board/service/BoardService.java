package com.zerobase.springbootjpapractice.board.service;

import com.zerobase.springbootjpapractice.board.entity.Board;
import com.zerobase.springbootjpapractice.board.entity.BoardBadReport;
import com.zerobase.springbootjpapractice.board.entity.BoardComment;
import com.zerobase.springbootjpapractice.board.entity.BoardType;
import com.zerobase.springbootjpapractice.board.model.*;

import java.util.List;

public interface BoardService {

    ServiceResult addBoard(BoardTypeInput input);

    ServiceResult updateBoard(long id, BoardTypeInput input);

    ServiceResult deleteBoard(Long id);

    List<BoardType> getAllBoardType();

    ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing);

    List<BoardTypeCount> getBoardTypeCount();

    ServiceResult setBoardTop(Long id, boolean flag);

    ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod);

    ServiceResult setBoardHits(Long id, String email);

    ServiceResult setBoardLike(Long id, String email);

    ServiceResult setBoardUnLike(Long id, String email);

    ServiceResult addBadReposrt(Long id, String email, BoardBadReportInput input);

    List<BoardBadReport> badReportList();

    ServiceResult scrapBoard(Long id, String email);

    ServiceResult removeBoard(Long id, String email);

    ServiceResult addBookmark(Long id, String email);

    ServiceResult removeBookmark(Long id, String email);

    List<Board> postList(String email);

    List<BoardComment> commentList(String email);

    Board detail(Long id);

    List<Board> list();

    ServiceResult add(String email, BoardInput input);

    ServiceResult replyBoard(Long id, BoardReplyInput input);
}

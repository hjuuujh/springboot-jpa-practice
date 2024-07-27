package com.zerobase.springbootjpapractice.board.service;

import com.zerobase.springbootjpapractice.board.entity.*;
import com.zerobase.springbootjpapractice.board.model.*;
import com.zerobase.springbootjpapractice.board.repository.*;
import com.zerobase.springbootjpapractice.common.exception.BizException;
import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BoardServiceImpl implements BoardService {
    private final BoardTypeRepository boardTypeRepository;
    private final BoardTypeCustomRepository boardTypeCustomRepository;
    private final BoardRepository boardRepository;
    private final BoardHitsRepository boardHitsRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardBadReportRepository boardBadReportRepository;
    private final BoardScarpRepository boardScarpRepository;
    private final BoardBookmarkRepository bookmarkRepository;
    private final BoardCommentRepository boardCommentRepository;

    private final UserRepository userRepository;

    @Override
    public ServiceResult addBoard(BoardTypeInput input) {
        BoardType boardType = boardTypeRepository.findByBoardName(input.getName());
        if (boardType != null) {
            return ServiceResult.fail("이미 동일한 게시판이름이 존재합니다.");
        }

        BoardType addBoardType = BoardType.builder()
                .boardName(boardType.getBoardName())
                .redDate(LocalDateTime.now())
                .build();
        boardTypeRepository.save(addBoardType);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult updateBoard(long id, BoardTypeInput input) {

        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if (optionalBoardType.isEmpty()) {
            return ServiceResult.fail("수정할 게시판 타입이 없습니다.");
        }
        BoardType boardType = optionalBoardType.get();
        if (input.getName().equals(boardType.getBoardName())) {
            return ServiceResult.fail("수정할 이름이 동일한 게시판명 입니다.");
        }
        boardType.setBoardName(input.getName());
        boardType.setUpdateDate(LocalDateTime.now());
        boardTypeRepository.save(boardType);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult deleteBoard(Long id) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if (optionalBoardType.isEmpty()) {
            return ServiceResult.fail("삭제할 게시판 타입이 없습니다.");
        }
        BoardType boardType = optionalBoardType.get();

        if(boardRepository.countByBoardType(boardType)>0){

            return ServiceResult.fail("삭제할 게시판 타입의 게시글이 존재합니다.");
        }
        boardTypeRepository.delete(boardType);
        return ServiceResult.success();
    }

    @Override
    public List<BoardType> getAllBoardType() {
        return boardTypeRepository.findAll();
    }

    @Override
    public ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if (optionalBoardType.isEmpty()) {
            return ServiceResult.fail("게시판 타입이 없습니다.");
        }

        BoardType boardType = optionalBoardType.get();
        boardType.setUsingYn(boardTypeUsing.isUsingYn());
        boardTypeRepository.save(boardType);

        return ServiceResult.success();
    }

    @Override
    public List<BoardTypeCount> getBoardTypeCount() {
        List<BoardTypeCount> boardTypeCount = boardTypeCustomRepository.getBoardTypeCount();
        return boardTypeCount;
    }

    @Override
    public ServiceResult setBoardTop(Long id, boolean topYn) {

        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();
        if(board.isTopYn() == topYn){
            if(topYn){

                return ServiceResult.fail("이미 게시글이 최상단에 배치되어 있습니다.");
            }else{
                return ServiceResult.fail("이미 게시글이 최상단 배치가 해제되어 있습니다.");

            }
        }

        board.setTopYn(topYn);
        boardRepository.save(board);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();
        board.setPublishStartDate(boardPeriod.getStartDate());
        board.setPublishEndDate(boardPeriod.getEndDate());
        boardRepository.save(board);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult setBoardHits(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        if(boardHitsRepository.countByBoardAndUser(board, user)>0){
            return ServiceResult.fail("이미 조회수가 있습니다.");
        }

        boardHitsRepository.save(BoardHits.builder()
                .board(board)
                .user(user)
                .regDate(LocalDateTime.now())
                .build());
        return ServiceResult.success();
    }

    @Override
    public ServiceResult setBoardLike(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        long boardLikeCount = boardLikeRepository.countByBoardAndUser(board, user);
        if(boardLikeCount>0){
            return ServiceResult.fail("이미 좋아요요한 내용이 있습니다.");
        }

        boardLikeRepository.save(BoardLike.builder()
                .board(board)
                .user(user)
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();
    }

    @Override
    public ServiceResult setBoardUnLike(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        Optional<BoardLike> optionalBoardLike = boardLikeRepository.findByBoardAndUser(board, user);

        if(optionalBoardLike.isEmpty()){
            return ServiceResult.fail("좋아요한 내용이 없습니다.");
        }
        BoardLike boardLike = optionalBoardLike.get();

        boardLikeRepository.delete(boardLike);

        return ServiceResult.success();

    }

    @Override
    public ServiceResult addBadReposrt(Long id, String email, BoardBadReportInput input) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        BoardBadReport boardBadReport = BoardBadReport.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .userEmail(user.getEmail())
                .boardUserId(board.getId())
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .boardContent(board.getContent())
                .boardRegDate(board.getRedDate())
                .comment(input.getComment())
                .regDate(LocalDateTime.now())
                .build();
        boardBadReportRepository.save(boardBadReport);
        return ServiceResult.success();
    }

    @Override
    public List<BoardBadReport> badReportList() {
        return boardBadReportRepository.findAll();
    }

    @Override
    public ServiceResult scrapBoard(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        BoardScrap boardScrap = BoardScrap.builder()
                .user(user)
                .boardId(board.getId())
                .boardTypeId(board.getBoardType().getId())
                .boardTitle(board.getTitle())
                .boardContent(board.getContent())
                .boardRegDate(board.getRedDate())
                .regDate(LocalDateTime.now())
                .build();

        boardScarpRepository.save(boardScrap);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult removeBoard(Long id, String email) {
        Optional<BoardScrap> optionalBoardScrap = boardScarpRepository.findById(id);
        if(optionalBoardScrap.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        BoardScrap boardScrap = optionalBoardScrap.get();

        if(!user.getId().equals(boardScrap.getUser().getId())){
            return ServiceResult.fail("본인의 스크랩만 삭제할 수 있습니다.");
        }

        boardScarpRepository.delete(boardScrap);
        return ServiceResult.success();
    }

    private String getBoardUrl(long boardId){
        return String.format("/board/%d", boardId);
    }

    @Override
    public ServiceResult addBookmark(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시판이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        BoardBookmark boardBookmark = BoardBookmark.builder()
                .user(user)
                .boardId(board.getId())
                .boardTypeId(board.getBoardType().getId())
                .boardTitle(board.getTitle())
                .boardUrl(getBoardUrl(board.getId()))
                .regDate(LocalDateTime.now())
                .build();
        bookmarkRepository.save(boardBookmark);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult removeBookmark(Long id, String email) {
        Optional<BoardBookmark> optionalBoardBookmark = bookmarkRepository.findById(id);
        if(optionalBoardBookmark.isEmpty()){
            return ServiceResult.fail("게시판이 존재하지 않습니다.");
        }
        BoardBookmark boardBookmark = optionalBoardBookmark.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        if(!user.getId().equals(boardBookmark.getUser().getId())){
            return ServiceResult.fail("본인의 북마크만 삭제할 수 있습니다.");
        }
        bookmarkRepository.delete(boardBookmark);
        return ServiceResult.success();

    }

    @Override
    public List<Board> postList(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new BizException("회원정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        List<Board> list = boardRepository.findByUser(user);
        return list;
    }

    @Override
    public List<BoardComment> commentList(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new BizException("회원정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        List<BoardComment> list = boardCommentRepository.findByUser(user);
        return list;
    }

    @Override
    public Board detail(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            throw new BizException("게시글이 존재하지 않습니다.");
        }

        return optionalBoard.get();
    }
}

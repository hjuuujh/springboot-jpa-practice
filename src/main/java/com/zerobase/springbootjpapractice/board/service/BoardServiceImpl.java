package com.zerobase.springbootjpapractice.board.service;

import com.zerobase.springbootjpapractice.board.entity.Board;
import com.zerobase.springbootjpapractice.board.entity.BoardType;
import com.zerobase.springbootjpapractice.board.model.BoardTypeCount;
import com.zerobase.springbootjpapractice.board.model.BoardTypeInput;
import com.zerobase.springbootjpapractice.board.model.BoardTypeUsing;
import com.zerobase.springbootjpapractice.board.model.ServiceResult;
import com.zerobase.springbootjpapractice.board.repository.BoardRepository;
import com.zerobase.springbootjpapractice.board.repository.BoardTypeCustomRepository;
import com.zerobase.springbootjpapractice.board.repository.BoardTypeRepository;
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
    public ServiceResult setBoardTop(Long id) {

        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();
        if(board.isTopYn()){
            return ServiceResult.fail("이미 게시글이 최상단에 배치되어 있습니다.");
        }

        board.setTopYn(true);
        boardRepository.save(board);
        return ServiceResult.success();
    }
}

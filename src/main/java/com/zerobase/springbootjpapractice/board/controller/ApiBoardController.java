package com.zerobase.springbootjpapractice.board.controller;

import com.zerobase.springbootjpapractice.board.entity.BoardType;
import com.zerobase.springbootjpapractice.board.model.BoardTypeCount;
import com.zerobase.springbootjpapractice.board.model.BoardTypeInput;
import com.zerobase.springbootjpapractice.board.model.BoardTypeUsing;
import com.zerobase.springbootjpapractice.board.model.ServiceResult;
import com.zerobase.springbootjpapractice.board.service.BoardService;
import com.zerobase.springbootjpapractice.notice.model.ResponseError;
import com.zerobase.springbootjpapractice.user.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiBoardController {
    private final BoardService boardService;

    @PostMapping("/api/board/type")
    public ResponseEntity<?> addBoardType(@RequestBody @Valid BoardTypeInput input
            , Errors errors) {

        if (errors.hasErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
            return new ResponseEntity <>(ResponseMessage.fail("입력값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = boardService.addBoard(input);

        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/board/type/{id}")
    public ResponseEntity<?> updateBoardType(@PathVariable Long id, @RequestBody @Valid BoardTypeInput input
            , Errors errors) {

        if (errors.hasErrors()) {
            List<ResponseError> responseErrors = ResponseError.of(errors.getAllErrors());
            return new ResponseEntity<>(ResponseMessage.fail("입력값이 정확하지 않습니다.", responseErrors), HttpStatus.BAD_REQUEST);
        }
        ServiceResult result = boardService.updateBoard(id, input);

        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/board/type/{id}")
    public ResponseEntity<?> deleteBoardType(@PathVariable Long id) {

        ServiceResult result = boardService.deleteBoard(id);
        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/board/type")
    public ResponseEntity<?> boardType(@PathVariable Long id) {

        List<BoardType> boardTypes = boardService.getAllBoardType();

        return ResponseEntity.ok().body(boardTypes);
    }

    @PatchMapping("/api/board/type/{id}/using")
    public ResponseEntity<?> usingBoardType(@PathVariable Long id, @RequestBody BoardTypeUsing boardTypeUsing) {

        ServiceResult result = boardService.setBoardTypeUsing(id, boardTypeUsing);

        if (!result.isResult()) {
            return ResponseEntity.ok().body(ResponseMessage.fail(result.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/board/type/count")
    public ResponseEntity<?> boardTypeCount(@PathVariable Long id, @RequestBody BoardTypeUsing boardTypeUsing) {

       List<BoardTypeCount> result = boardService.getBoardTypeCount();


        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/api/board/{id}/top")
    public ResponseEntity<?> boardPostTop(@PathVariable Long id) {

        ServiceResult result = boardService.setBoardTop(id);


        return ResponseEntity.ok().body(result);
    }

}

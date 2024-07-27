package com.zerobase.springbootjpapractice.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zerobase.springbootjpapractice.board.entity.Board;
import com.zerobase.springbootjpapractice.board.entity.BoardType;
import com.zerobase.springbootjpapractice.board.model.*;
import com.zerobase.springbootjpapractice.board.service.BoardService;
import com.zerobase.springbootjpapractice.common.exception.BizException;
import com.zerobase.springbootjpapractice.common.model.ResponseResult;
import com.zerobase.springbootjpapractice.notice.model.ResponseError;
import com.zerobase.springbootjpapractice.common.model.ResponseMessage;
import com.zerobase.springbootjpapractice.util.JWTUtils;
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

        ServiceResult result = boardService.setBoardTop(id, true);


        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/api/board/{id}/top/clear")
    public ResponseEntity<?> boardPostTopClear(@PathVariable Long id) {

        ServiceResult result = boardService.setBoardTop(id, false);


        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/api/board/{id}/publish")
    public ResponseEntity<?> boardPeriod(@PathVariable Long id, @RequestBody BoardPeriod boardPeriod) {

        ServiceResult result = boardService.setBoardPeriod(id, boardPeriod);

        if(!result.isResult()){
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success();
    }

    @PutMapping("/api/board/{id}/hits")
    public ResponseEntity<?> boardHits(@PathVariable Long id, @RequestHeader("F-TOKEN") String token) {

        String email;
        try {

            email = JWTUtils.getIssuer(token);
        }catch (JWTVerificationException e){
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result = boardService.setBoardHits(id, email);
        if(!result.isResult()){
            return ResponseResult.fail(result.getMessage());
        }
        return ResponseResult.success();
    }

    @PutMapping("/api/board/{id}/like")
    public ResponseEntity<?> boardLike(@PathVariable Long id, @RequestHeader("F-TOKEN") String token) {

        String email;
        try {

            email = JWTUtils.getIssuer(token);
        }catch (JWTVerificationException e){
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result = boardService.setBoardLike(id, email);

        return ResponseResult.result(result);
    }

    @PutMapping("/api/board/{id}/unlike")
    public ResponseEntity<?> boardUnLike(@PathVariable Long id, @RequestHeader("F-TOKEN") String token) {

        String email;
        try {

            email = JWTUtils.getIssuer(token);
        }catch (JWTVerificationException e){
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result = boardService.setBoardUnLike(id, email);

        return ResponseResult.result(result);
    }

    @PutMapping("/api/board/{id}/badreport")
    public ResponseEntity<?> boardBadReport(@PathVariable Long id,
                                            @RequestHeader("F-TOKEN") String token,
                                            @RequestBody BoardBadReportInput input) {

        String email;
        try {

            email = JWTUtils.getIssuer(token);
        }catch (JWTVerificationException e){
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result = boardService.addBadReposrt(id, email, input);

        return ResponseResult.result(result);
    }

    @GetMapping("/api/board/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id){
        Board board;
        try {
            board = boardService.detail(id);
        }catch (BizException e){
            return ResponseResult.fail(e.getMessage());
        }
        return ResponseResult.success(board);

    }
}

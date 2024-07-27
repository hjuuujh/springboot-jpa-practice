package com.zerobase.springbootjpapractice.board.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zerobase.springbootjpapractice.board.model.ServiceResult;
import com.zerobase.springbootjpapractice.board.service.BoardService;
import com.zerobase.springbootjpapractice.common.model.ResponseResult;
import com.zerobase.springbootjpapractice.util.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ApiBoardScrapController {
    private final BoardService boardService;

    @PutMapping("/api/board/{id}/scrap")
    public ResponseEntity<?> boardScrap(@PathVariable Long id
            , @RequestHeader("F-TOKEN") String token) {

        String email;
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.scrapBoard(id, email);

        return ResponseResult.result(result);
    }

    @DeleteMapping("/api/scarp/{id}")
    public ResponseEntity<?> deleteBoardScrap(@PathVariable Long id
            , @RequestHeader("F-TOKEN") String token) {

        String email;
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        ServiceResult result = boardService.removeBoard(id, email);

        return ResponseResult.result(result);
    }

}

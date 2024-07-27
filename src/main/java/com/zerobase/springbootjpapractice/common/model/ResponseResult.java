package com.zerobase.springbootjpapractice.common.model;

import com.zerobase.springbootjpapractice.board.entity.BoardBadReport;
import com.zerobase.springbootjpapractice.board.model.ServiceResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseResult {
    public static ResponseEntity<?> success() {
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    public static ResponseEntity<?> success(Object data) {
        return ResponseEntity.ok().body(ResponseMessage.success(data));
    }


    public static ResponseEntity<?> fail(String message) {
        return fail(message, null);
    }

    public static ResponseEntity<?> fail(String message, Object data) {
        return ResponseEntity.badRequest().body(ResponseMessage.fail(message, data));
    }

    public static ResponseEntity<?> result(ServiceResult result) {
        if(!result.isResult()){
            return fail(result.getMessage());
        }
        return success();
    }
}

package com.zerobase.springbootjpapractice.common.model;

import com.zerobase.springbootjpapractice.board.entity.BoardBadReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessage {
//    private List<User> data;
//    private long count;

    ResponseMessageHeader header;
    Object body;

    public static ResponseMessage fail(String message) {
        return fail(message, null);
    }

    public static ResponseMessage fail(String message, Object data) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(false)
                        .code("")
                        .message(message)
                        .status(HttpStatus.OK.value())
                        .build())
                .body(data)
                .build();
    }

    public static ResponseMessage success(Object object) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .code("")
                        .message("")
                        .status(HttpStatus.OK.value()).build())
                .body(object).build();
    }

    public static ResponseMessage success(List<BoardBadReport> object) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .code("")
                        .message("")
                        .status(HttpStatus.OK.value()).build())
                .body(object).build();
    }


    public static ResponseMessage success() {
        return success(Optional.ofNullable(null));
    }
}

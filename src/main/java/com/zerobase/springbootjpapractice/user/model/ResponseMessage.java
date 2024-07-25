package com.zerobase.springbootjpapractice.user.model;

import com.zerobase.springbootjpapractice.user.entity.User;
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

    public static Object fail(String message) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(false)
                        .code("")
                        .message(message)
                        .status(HttpStatus.BAD_REQUEST.value()).build());
    }

    public static Object success(Optional object) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .code("")
                        .message("")
                        .status(HttpStatus.OK.value()).build())
                .body(object).build();
    }


    public static Object success() {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .code("")
                        .message("")
                        .status(HttpStatus.OK.value()).build())
                .body(null).build();
    }
}

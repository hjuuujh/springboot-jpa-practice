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

    public static ResponseMessage success(Optional object) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .code("")
                        .message("")
                        .status(HttpStatus.OK.value()).build())
                .body(object).build();
    }


    public static ResponseMessage success() {
        return success(null);
    }
}

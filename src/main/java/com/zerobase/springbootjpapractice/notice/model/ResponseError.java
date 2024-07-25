package com.zerobase.springbootjpapractice.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseError {
    private String field;
    private String message;

    public static ResponseError of(FieldError error) {
        return ResponseError.builder()
                .field(error.getField())
                .message(error.getDefaultMessage())
                .build();
    }
}

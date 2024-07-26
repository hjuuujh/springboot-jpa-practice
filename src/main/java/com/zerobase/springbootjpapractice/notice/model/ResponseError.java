package com.zerobase.springbootjpapractice.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

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

    public static List<ResponseError> of(List<ObjectError> errors) {
        List<ResponseError> responseErrors = new ArrayList<>();
        if(errors != null) {
            errors.stream().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });
        }

        return responseErrors;

    }
}

package com.zerobase.springbootjpapractice.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeInput {
    @NotBlank(message = "제목은 필수입니다.")
    @Size(min =10, max = 100, message = "제목은 10-100자 사이 값입니다.")
    private String title;
    @NotBlank(message = "내용은 필수입니다.")
    @Size(min =50, max = 1000, message = "내용은 50-1000자 사이 값입니다.")
    private String contents;
}

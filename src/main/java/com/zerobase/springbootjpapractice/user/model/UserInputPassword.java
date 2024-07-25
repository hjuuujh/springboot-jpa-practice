package com.zerobase.springbootjpapractice.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInputPassword {
    @NotBlank(message = "현재 비밀번호는 필수 항목입니다.")
    private String newPassword;
    @NotBlank(message = "신규 비밀번호는 필수 항목입니다.")
    @Size(min = 4, max = 20, message = "신규 비밀번호는 4-20사이의 길이로 입력해 주세요.")
    private String password;
}

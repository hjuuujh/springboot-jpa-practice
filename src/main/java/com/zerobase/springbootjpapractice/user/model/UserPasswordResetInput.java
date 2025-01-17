package com.zerobase.springbootjpapractice.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPasswordResetInput {
    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    private String email;

    @NotBlank(message = "유저 이름은 필수 입력사항입니다.")
    private String userName;

}

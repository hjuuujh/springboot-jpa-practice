package com.zerobase.springbootjpapractice.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdate {
    @NotBlank(message = "연락처는 필수 항목입니다.")
    @Size(max = 20, message = "연락처는 최대 20자까지 입력해야합니다.")
    private String phone;
}

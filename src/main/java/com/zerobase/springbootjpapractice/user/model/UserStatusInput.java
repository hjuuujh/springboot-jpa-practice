package com.zerobase.springbootjpapractice.user.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatusInput {
    private UserStatus status;
}

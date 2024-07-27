package com.zerobase.springbootjpapractice.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserPointInput {
    private UserPointType userPointType;
}

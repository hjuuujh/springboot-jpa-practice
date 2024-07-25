package com.zerobase.springbootjpapractice.user.model;

import lombok.Getter;

@Getter
public enum UserStatus {
    NONE, USING, STOP;
    int value;
    UserStatus() {}
}

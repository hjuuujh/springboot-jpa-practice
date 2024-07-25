package com.zerobase.springbootjpapractice.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

@UtilityClass
public class PasswordUtils {

    // pw 암호화해 리턴
    public static boolean equalPassword(String password, String encryptedPassword) {
        return BCrypt.checkpw(password, encryptedPassword);
    }
    // 입력한 패스워드를 해시된 패스워드와 비교
}

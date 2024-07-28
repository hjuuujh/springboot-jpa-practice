package com.zerobase.springbootjpapractice.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.model.UserLoginToken;
import lombok.experimental.UtilityClass;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@UtilityClass
public class JWTUtils {
    private static final String KEY = "zerobase";
    private static final String CLAIM_USER_ID = "user_id";

    public static String getIssuer(String token) {

        return JWT.require(Algorithm.HMAC512(KEY.getBytes()))
                .build()
                .verify(token)
                .getIssuer();
    }

    public static UserLoginToken createToken(User user) {
        if (user == null) {
            return null;
        }
        LocalDateTime expiration = LocalDateTime.now().plusMonths(1);
        Date expiredDate = Timestamp.valueOf(expiration);
        String token = JWT.create()
                .withExpiresAt(expiredDate)
                .withClaim(CLAIM_USER_ID, user.getId())
                .withSubject(user.getUserName())
                .withIssuer(user.getEmail())
                .sign(Algorithm.HMAC512(KEY.getBytes()));
        return UserLoginToken.builder()
                .token(token)
                .build();
    }

    private boolean vaildJWT(HttpServletRequest request) {
        String token = request.getHeader("F-TOKEN");
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        }catch (JWTVerificationException | SignatureGenerationException e){
            return false;
        }

        return true;
    }
}

package com.zerobase.springbootjpapractice.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("naver-api")
@Data
@Component
public class NaverAopProperties {
    private String clientId;
    private String clientSecret;
}

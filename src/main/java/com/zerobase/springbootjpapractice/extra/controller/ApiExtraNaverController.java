package com.zerobase.springbootjpapractice.extra.controller;

import com.zerobase.springbootjpapractice.common.model.ResponseResult;
import com.zerobase.springbootjpapractice.common.properties.NaverAopProperties;
import com.zerobase.springbootjpapractice.extra.model.KakaoTranslateInput;
import com.zerobase.springbootjpapractice.extra.model.NaverTranslateInput;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiExtraNaverController {

    private final NaverAopProperties naverAopProperties;

    @PostMapping("/api/extra/naver/translate")
    public ResponseEntity<?> translate(@RequestBody NaverTranslateInput input) {
        String url = "https://openapi.naver.com/v1/search/translate";
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("source", "ko");
        params.add("target", "en");
        params.add("text", input.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-Naver-Client-Id", naverAopProperties.getClientId());
        headers.add("X-Naver-Client-Secret", naverAopProperties.getClientSecret());

        HttpEntity formEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, formEntity, String.class);

        return ResponseResult.success(response.getBody());
    }

}

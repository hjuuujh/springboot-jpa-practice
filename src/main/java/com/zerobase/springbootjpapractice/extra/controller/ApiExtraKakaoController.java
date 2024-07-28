package com.zerobase.springbootjpapractice.extra.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.springbootjpapractice.common.model.ResponseResult;
import com.zerobase.springbootjpapractice.extra.model.AirInput;
import com.zerobase.springbootjpapractice.extra.model.KakaoTranslateInput;
import com.zerobase.springbootjpapractice.extra.model.OpenApiResult;
import com.zerobase.springbootjpapractice.extra.model.PharmacySearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiExtraKakaoController {

    @Value("${spring.extra.kakao}")
    private String EXTRA_KAKAO_KEY;

    @PostMapping("/api/extra/kakao/translate")
    public ResponseEntity<?> translate(@RequestBody KakaoTranslateInput input) {
        String url = "https://kapi.kakao.com/v2/translate";
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("src_lang", "kr");
        params.add("target_lang", "en");
        params.add("query", input.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("AUTHORIZATION", "KakaoAK " + EXTRA_KAKAO_KEY);

        HttpEntity formEntity = new HttpEntity<>(params, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, formEntity, String.class);

        return ResponseResult.success(response.getBody());
    }

}

package com.zerobase.springbootjpapractice.extra.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.springbootjpapractice.common.model.ResponseResult;
import com.zerobase.springbootjpapractice.extra.model.AirInput;
import com.zerobase.springbootjpapractice.extra.model.OpenApiResult;
import com.zerobase.springbootjpapractice.extra.model.PharmacySearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
//import java.net.URLEncoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ApiExtraController {

    @Value("${spring.extra.key}")
    private String EXTRA_KEY;

    @GetMapping("/api/extra/pharmacy")
    public ResponseEntity<?> pharmacy() {

        String url = "https://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?serviceKey=%s&pageNo=1&numOfRows=10";
        String apiResult = "";
        try {
            URI uri = new URI(String.format(url, EXTRA_KEY));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            apiResult = restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OpenApiResult jsonResult = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseResult.success(jsonResult);
    }

    @GetMapping("/api/extra/pharmacy/search")
    public ResponseEntity<?> pharmacyWithSearch(@RequestBody PharmacySearch pharmacySearch) {

        String url = String.format("https://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown?serviceKey=%s&pageNo=1&numOfRows=10", EXTRA_KEY);
        String apiResult = "";
        try {
            url += String.format("&Q0=%s&Q1=%s"
                    , URLEncoder.encode(pharmacySearch.getSearchSido(), "UTF-8")
                    , URLEncoder.encode(pharmacySearch.getSearchGugun(), "UTF-8"));
            URI uri = new URI(url);
            log.info(uri.toString());
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            apiResult = restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OpenApiResult jsonResult = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseResult.success(jsonResult);
    }

    @GetMapping("/api/extra/air")
    public ResponseEntity<?> air(@RequestBody AirInput airInput) {
        String url = "https://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?serviceKey=%s&returnType=xml&numOfRows=100&pageNo=1&sidoName=%s&ver=1.0";
        String apiResult = "";
        try {
            URI uri = new URI(String.format(url, EXTRA_KEY, URLEncoder.encode(airInput.getSearchSido(), StandardCharsets.UTF_8)));
            log.info(String.valueOf(uri));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            apiResult = restTemplate.getForObject(uri, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OpenApiResult jsonResult = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return ResponseResult.success(jsonResult);
    }

}

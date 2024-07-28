package com.zerobase.springbootjpapractice.logs.service;

import com.zerobase.springbootjpapractice.logs.entity.Logs;
import com.zerobase.springbootjpapractice.logs.repository.LogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogService{
    private final LogsRepository logsRepository;

    @Override
    public void add(String text) {
        logsRepository.save(Logs.builder()
                .text(text)
                .regDate(LocalDateTime.now())
                .build());
    }

    @Override
    public void deleteLog() {
        logsRepository.deleteAll();
    }
}

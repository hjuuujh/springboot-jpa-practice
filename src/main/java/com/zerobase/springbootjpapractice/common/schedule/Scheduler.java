package com.zerobase.springbootjpapractice.common.schedule;

import com.zerobase.springbootjpapractice.logs.service.LogService;
import com.zerobase.springbootjpapractice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final LogService logService;
    private final UserService userService;

    @Scheduled(cron = "0 0 4 * * *")
    public void deleteLog(){
        logService.deleteLog();
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void sendServiceNotice(){
        userService.sendServiceNotice();
    }
}

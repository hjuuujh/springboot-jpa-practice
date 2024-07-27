package com.zerobase.springbootjpapractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaRepositories
public class SpringbootJpaPracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaPracticeApplication.class, args);
    }

}

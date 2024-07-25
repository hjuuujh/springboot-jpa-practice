package com.zerobase.springbootjpapractice.user.entity;

import com.zerobase.springbootjpapractice.user.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String email;
    private String userName;
    private String password;
    private String phone;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private UserStatus status;
    private boolean lockYn;

}

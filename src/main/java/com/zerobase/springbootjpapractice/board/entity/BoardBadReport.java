package com.zerobase.springbootjpapractice.board.entity;

import com.zerobase.springbootjpapractice.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class BoardBadReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;
    private String userName;
    private String userEmail;

    private long boardId;
    private long boardUserId;
    private LocalDateTime boardRegDate;
    private String boardTitle;
    private String boardContent;

    private String comment;
    private LocalDateTime regDate;

}

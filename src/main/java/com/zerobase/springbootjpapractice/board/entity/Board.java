package com.zerobase.springbootjpapractice.board.entity;

import com.zerobase.springbootjpapractice.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private BoardType boardType;

    private String title;
    private String content;
    private LocalDateTime redDate;
    private LocalDateTime updateDate;

    private boolean topYn;

    private LocalDate publishStartDate;
    private LocalDate publishEndDate;

    private String replyContents;
}

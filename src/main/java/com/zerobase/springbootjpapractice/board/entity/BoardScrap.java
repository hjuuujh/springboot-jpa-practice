package com.zerobase.springbootjpapractice.board.entity;

import com.zerobase.springbootjpapractice.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class BoardScrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;

    private long boardId;
    private long boardTypeId;
    private long boardUserId;
    private LocalDateTime boardRegDate;
    private String boardTitle;
    private String boardContent;

    private LocalDateTime regDate;

}

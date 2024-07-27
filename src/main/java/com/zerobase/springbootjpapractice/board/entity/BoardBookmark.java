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
public class BoardBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private User user;

    private long boardId;
    private long boardTypeId;
    private String boardTitle;
    private String boardUrl;

    private LocalDateTime regDate;

}

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
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn
    private Board board;
    @JoinColumn
    @ManyToOne
    private User user;

    private LocalDateTime regDate;
}

package com.zerobase.springbootjpapractice.user.entity;

import com.zerobase.springbootjpapractice.user.model.UserPointType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class UserPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @Enumerated(EnumType.STRING)
    private UserPointType userPointType;
    private int point;
}

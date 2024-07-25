package com.zerobase.springbootjpapractice.notice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeModel {
    // ID, 제목, 내용, 등록일

    private int id;
    private String title;
    private String contents;
    private LocalDateTime regDate;
}

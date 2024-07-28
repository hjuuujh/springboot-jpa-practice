package com.zerobase.springbootjpapractice.board.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class BoardInput {
    private long boardType;
    private String title;
    private String content;
}

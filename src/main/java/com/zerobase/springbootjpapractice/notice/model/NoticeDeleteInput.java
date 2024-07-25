package com.zerobase.springbootjpapractice.notice.model;

import lombok.Data;

import java.util.List;

@Data
public class NoticeDeleteInput {
    private List<Long> idList;
}

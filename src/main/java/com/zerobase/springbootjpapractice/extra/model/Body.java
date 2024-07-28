package com.zerobase.springbootjpapractice.extra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Body {

    private Items items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}

package com.zerobase.springbootjpapractice.extra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenApiResultResponse {
    private Header header;
    private Body body;
}

package com.zerobase.springbootjpapractice.extra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AirInput {
    private String sido;
    public String getSearchSido(){
        return sido != null ? sido : "";
    }
}

package com.zerobase.springbootjpapractice.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardTypeCount {
    private long id;
    private String boardName;
    private LocalDateTime redDate;
    private boolean usingYn;
    private long boardCount;

    public BoardTypeCount(Object[] arrObj) {
        this.id = ((BigInteger) arrObj[0]).longValue();
        this.boardName = (String) arrObj[1];
        this.redDate = (LocalDateTime) arrObj[2];
        this.usingYn = (boolean) arrObj[3];
        this.boardCount = ((BigInteger) arrObj[4]).longValue();
    }

    public BoardTypeCount(long id, String boardName, LocalDateTime redDate, boolean usingYn, long boardCount) {
        this.id = id;
        this.boardName = boardName;
        this.redDate = redDate;
        this.usingYn = usingYn;
        this.boardCount = boardCount;
    }
}

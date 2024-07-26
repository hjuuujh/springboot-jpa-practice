package com.zerobase.springbootjpapractice.board.repository;

import com.zerobase.springbootjpapractice.board.model.BoardTypeCount;
import com.zerobase.springbootjpapractice.user.model.UserLogCount;
import com.zerobase.springbootjpapractice.user.model.UserNoticeCount;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BoardTypeCustomRepository {
    private final EntityManager em;

    public List<BoardTypeCount> getBoardTypeCount(){
        String sql = "select bt.id, bt.board_name, bt.rg_date, bt.using_yn, " +
                "(select count(*) from board b where b.board_type_id = bt.id) as board_count " +
                "from board_type bt";

//        List<Object[]> result = em.createNativeQuery(sql).getResultList();
//        List<BoardTypeCount> reultList = result.stream()
//                .map(re -> new BoardTypeCount(re))
//                .collect(Collectors.toList());

        // 주석 처리된 부분 라이브러리 이용해 구현
        Query nativeQuery = em.createNativeQuery(sql);
        JpaResultMapper jpaResultMapper = new JpaResultMapper();
        List<BoardTypeCount> reultList = jpaResultMapper.list(nativeQuery, BoardTypeCount.class);
        return reultList;
    }

}

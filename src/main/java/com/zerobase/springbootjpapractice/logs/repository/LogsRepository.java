package com.zerobase.springbootjpapractice.logs.repository;

import com.zerobase.springbootjpapractice.logs.entity.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {
}

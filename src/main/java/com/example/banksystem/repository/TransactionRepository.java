package com.example.banksystem.repository;

import com.example.banksystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("select t from Transaction t where (t.accountFrom.id = :accountId or t.accountTo.id = :accountId) and t.timestamp between :start and :end order by t.timestamp desc")
    List<Transaction> findByAccountIdAndTimestampBetween(@Param("accountId") Long accountId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("select t from Transaction t where t.accountFrom.id = :accountId or t.accountTo.id = :accountId order by t.timestamp desc")
    List<Transaction> findByAccountId(@Param("accountId") Long accountId);
}

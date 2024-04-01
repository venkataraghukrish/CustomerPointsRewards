package com.transaction.rewards.repository;

import com.transaction.rewards.model.Transaction;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String > {
        @Query(value = "Select * from TRANSACTION WHERE custId = ? and dateTime between ? and ?", nativeQuery = true)
        List<Transaction> findTransactionsByIdAndMonth(String id, Calendar startTime, Calendar endTime);
}

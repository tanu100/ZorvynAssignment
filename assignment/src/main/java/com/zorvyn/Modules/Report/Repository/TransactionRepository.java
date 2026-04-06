package com.zorvyn.Modules.Report.Repository;

import com.zorvyn.Modules.Report.Models.Transaction;
import com.zorvyn.Modules.Shared.Enums.TransactionType;
import com.zorvyn.Modules.User.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    List<Transaction> findAllByDeletedFalse();

    Optional<Transaction> findByIdAndDeletedFalse(Long id);



    List<Transaction> findByCreatedByAndDeletedFalse(User user);

    List<Transaction> findByCreatedById(Long userId);



    List<Transaction> findTop5ByDeletedFalseOrderByDateDesc();

    List<Transaction> findTop5ByCreatedByAndDeletedFalseOrderByDateDesc(User user);



    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.type='INCOME' AND t.deleted=false")
    Double getTotalIncome();

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.type='EXPENSE' AND t.deleted=false")
    Double getTotalExpense();

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.type='INCOME' AND t.createdBy.id=:userId AND t.deleted=false")
    Double getUserIncome(Long userId);

    @Query("SELECT COALESCE(SUM(t.amount),0) FROM Transaction t WHERE t.type='EXPENSE' AND t.createdBy.id=:userId AND t.deleted=false")
    Double getUserExpense(Long userId);
}
package com.zorvyn.Modules.Report.Repository;

import com.zorvyn.Modules.Report.Models.Transaction;
import com.zorvyn.Modules.Report.Models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // --- Basic finders (exclude soft-deleted records) ---

    List<Transaction> findAllByDeletedFalse();

    Optional<Transaction> findByIdAndDeletedFalse(Long id);

    // --- Filtering ---

    List<Transaction> findAllByTypeAndDeletedFalse(TransactionType type);

    List<Transaction> findAllByCategoryIgnoreCaseAndDeletedFalse(String category);

    List<Transaction> findAllByDateBetweenAndDeletedFalse(LocalDate from, LocalDate to);

    List<Transaction> findAllByTypeAndCategoryIgnoreCaseAndDeletedFalse(
            TransactionType type, String category);

    List<Transaction> findAllByTypeAndDateBetweenAndDeletedFalse(
            TransactionType type, LocalDate from, LocalDate to);

    List<Transaction> findAllByCategoryIgnoreCaseAndDateBetweenAndDeletedFalse(
            String category, LocalDate from, LocalDate to);

    List<Transaction> findAllByTypeAndCategoryIgnoreCaseAndDateBetweenAndDeletedFalse(
            TransactionType type, String category, LocalDate from, LocalDate to);

    // NOTE: Aggregate queries (sumByType, sumByCategory, sumByMonth, findTop10)
    // have been moved to DashboardRepository in the Dashboard module.
}
package com.zorvyn.Modules.Dashboard.Service;

import com.zorvyn.Modules.Dashboard.Dtos.DashboardResponse;
import com.zorvyn.Modules.Report.DTOs.TransactionResponseDto;
import com.zorvyn.Modules.Report.Models.Transaction;
import com.zorvyn.Modules.Report.Repository.TransactionRepository;
import com.zorvyn.Modules.Report.Services.TransactionService;
import com.zorvyn.Modules.Security.CustomUserDetails;
import com.zorvyn.Modules.Shared.Enums.Role;
import com.zorvyn.Modules.User.Models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TransactionRepository repo;
    private final TransactionService transactionService;


    private User getCurrentUser() {
        return ((CustomUserDetails)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal())
                .getUser();
    }

    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public DashboardResponse getDashboard() {

        User user = getCurrentUser();


        Double income = Optional.ofNullable(transactionService.getTotalIncome()).orElse(0.0);
        Double expense = Optional.ofNullable(transactionService.getTotalExpense()).orElse(0.0);

        Double net = income - expense;


        List<Transaction> transactions =
                (user.getRole() == Role.ADMIN || user.getRole() == Role.ANALYST)
                        ? repo.findAllByDeletedFalse()
                        : repo.findByCreatedByAndDeletedFalse(user);


        Map<String, Map<String, Double>> categoryTotals =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                Transaction::getCategory,
                                Collectors.groupingBy(
                                        t -> t.getType().name(),
                                        Collectors.summingDouble(Transaction::getAmount)
                                )
                        ));


        Map<String, Map<String, Double>> monthlyTrends =
                transactions.stream()
                        .collect(Collectors.groupingBy(
                                t -> t.getDate().getMonth().toString(),
                                Collectors.groupingBy(
                                        t -> t.getType().name(),
                                        Collectors.summingDouble(Transaction::getAmount)
                                )
                        ));

        List<TransactionResponseDto> recent =
                transactionService.getRecentTransactions();

        return DashboardResponse.builder()
                .totalIncome(income)
                .totalExpense(expense)
                .netBalance(net)
                .categoryTotals(categoryTotals)
                .monthlyTrends(monthlyTrends)
                .recentTransactions(recent)
                .build();
    }
}
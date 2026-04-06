package com.zorvyn.Modules.Dashboard.Dtos;

import com.zorvyn.Modules.Report.DTOs.TransactionResponseDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardResponse {

    private Double totalIncome;
    private Double totalExpense;
    private Double netBalance;



    private List<TransactionResponseDto> recentTransactions;

    private Map<String, Map<String, Double>> categoryTotals;
    private Map<String, Map<String, Double>> monthlyTrends;
}
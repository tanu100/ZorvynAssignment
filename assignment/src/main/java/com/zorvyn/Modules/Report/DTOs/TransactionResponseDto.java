package com.zorvyn.Modules.Report.DTOs;

import com.zorvyn.Modules.Report.Models.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class TransactionResponseDto {

    private Long id;
    private Double amount;
    private TransactionType type;
    private String category;
    private LocalDate date;
    private String notes;
}
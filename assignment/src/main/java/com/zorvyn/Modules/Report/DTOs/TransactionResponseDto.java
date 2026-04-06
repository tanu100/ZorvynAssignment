package com.zorvyn.Modules.Report.DTOs;

import com.zorvyn.Modules.Shared.Enums.TransactionType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Builder
public class TransactionResponseDto {

    private Long id;
    private Double amount;
    private TransactionType type;
    private String category;
    private LocalDate date;
    private String notes;

    private Long createdBy;
}
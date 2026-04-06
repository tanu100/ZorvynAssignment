package com.zorvyn.Modules.Report.Controllers;

import com.zorvyn.Modules.Report.DTOs.TransactionDto;
import com.zorvyn.Modules.Report.DTOs.TransactionResponseDto;
import com.zorvyn.Modules.Shared.Enums.TransactionType;
import com.zorvyn.Modules.Report.Services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions") // 🔥 better naming
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    @PostMapping
    public ResponseEntity<TransactionResponseDto> create(
            @RequestBody @Valid TransactionDto dto) {

        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> update(
            @PathVariable Long id,
            @RequestBody @Valid TransactionDto dto) {

        return ResponseEntity.ok(service.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }


    @GetMapping("/filter")
    public ResponseEntity<List<TransactionResponseDto>> filter(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        return ResponseEntity.ok(service.filter(type, category, from, to));
    }


    @GetMapping("/dashboard/income")
    public ResponseEntity<Double> getTotalIncome() {
        return ResponseEntity.ok(service.getTotalIncome());
    }

    @GetMapping("/dashboard/expense")
    public ResponseEntity<Double> getTotalExpense() {
        return ResponseEntity.ok(service.getTotalExpense());
    }

    @GetMapping("/dashboard/recent")
    public ResponseEntity<List<TransactionResponseDto>> getRecentTransactions() {
        return ResponseEntity.ok(service.getRecentTransactions());
    }
}
package com.zorvyn.Modules.Report.Controllers;

import com.zorvyn.Modules.Report.DTOs.TransactionDto;
import com.zorvyn.Modules.Report.DTOs.TransactionResponseDto;
import com.zorvyn.Modules.Report.Models.TransactionType;
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
@RequestMapping("/reports")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;


    @PostMapping
    public ResponseEntity<TransactionResponseDto> create(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid TransactionDto dto) {
        return new ResponseEntity<>(service.create(userId, dto), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getAll(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(service.getAll(userId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getById(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(userId, id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> update(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @RequestBody @Valid TransactionDto dto) {
        return ResponseEntity.ok(service.update(userId, id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.delete(userId, id));
    }


    @GetMapping("/filter")
    public ResponseEntity<List<TransactionResponseDto>> filter(
            @RequestHeader("X-User-Id") Long userId,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(service.filter(userId, type, category, from, to));
    }

}
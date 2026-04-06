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

/**
 * All endpoints require a userId header so the service layer can
 * resolve the caller's role and status.
 *
 * Header: X-User-Id: <userId>
 */
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService service;

    // -------------------------------------------------------------------------
    // CRUD  (ADMIN only for write operations)
    // -------------------------------------------------------------------------

    /**
     * POST /reports
     * Create a new financial record. ADMIN only.
     *
     * Body: { "amount": 5000, "type": "INCOME", "category": "Salary",
     *         "date": "2025-04-01", "notes": "April salary" }
     */
    @PostMapping
    public ResponseEntity<TransactionResponseDto> create(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid TransactionDto dto) {
        return new ResponseEntity<>(service.create(userId, dto), HttpStatus.CREATED);
    }

    /**
     * GET /reports
     * Get all financial records. All roles (VIEWER, ANALYST, ADMIN).
     */
    @GetMapping
    public ResponseEntity<List<TransactionResponseDto>> getAll(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(service.getAll(userId));
    }

    /**
     * GET /reports/{id}
     * Get a single record by ID. All roles.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> getById(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(userId, id));
    }

    /**
     * PUT /reports/{id}
     * Update a record. ADMIN only.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDto> update(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @RequestBody @Valid TransactionDto dto) {
        return ResponseEntity.ok(service.update(userId, id, dto));
    }

    /**
     * DELETE /reports/{id}
     * Soft-delete a record. ADMIN only.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.delete(userId, id));
    }

    // -------------------------------------------------------------------------
    // Filtering  (All roles)
    // -------------------------------------------------------------------------

    /**
     * GET /reports/filter?type=INCOME&category=Salary&from=2025-01-01&to=2025-04-30
     *
     * All query params are optional — use any combination.
     * All roles can filter.
     */
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
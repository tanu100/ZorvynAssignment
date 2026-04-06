package com.zorvyn.Modules.Report.Services;

import com.zorvyn.Modules.Report.DTOs.TransactionDto;
import com.zorvyn.Modules.Report.DTOs.TransactionResponseDto;
import com.zorvyn.Modules.Report.Models.Transaction;
import com.zorvyn.Modules.Report.Repository.TransactionRepository;
import com.zorvyn.Modules.Security.CustomUserDetails;
import com.zorvyn.Modules.Shared.Enums.Role;
import com.zorvyn.Modules.Shared.Enums.TransactionType;
import com.zorvyn.Modules.Shared.Exception.Errors.TransactionNotFoundException;
import com.zorvyn.Modules.User.Models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repo;

    private User getCurrentUser() {
        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();

        return userDetails.getUser();
    }


    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public TransactionResponseDto create(TransactionDto dto) {

        User user = getCurrentUser();

        Transaction t = new Transaction();
        t.setAmount(dto.getAmount());
        t.setType(dto.getType());
        t.setCategory(dto.getCategory());
        t.setDate(dto.getDate());
        t.setNotes(dto.getNotes());
        t.setCreatedBy(user);

        return toDto(repo.save(t));
    }


    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public List<TransactionResponseDto> getAll() {

        User user = getCurrentUser();

        if (user.getRole() == Role.ADMIN || user.getRole() == Role.ANALYST) {
            return repo.findAllByDeletedFalse()
                    .stream()
                    .map(this::toDto)
                    .toList();
        }

        return repo.findByCreatedByAndDeletedFalse(user)
                .stream()
                .map(this::toDto)
                .toList();
    }


    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public TransactionResponseDto getById(Long id) {

        User user = getCurrentUser();

        Transaction t = repo.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        // VIEWER → only own
        if (user.getRole() == Role.VIEWER &&
                !t.getCreatedBy().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to access this transaction");
        }

        return toDto(t);
    }


    @PreAuthorize("hasAnyRole('VIEWER','ADMIN')")
    public TransactionResponseDto update(Long id, TransactionDto dto) {

        User user = getCurrentUser();

        Transaction t = repo.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        // VIEWER → only own
        if (user.getRole() == Role.VIEWER &&
                !t.getCreatedBy().getId().equals(user.getId())) {
            throw new AccessDeniedException("You are not allowed to update this transaction");
        }

        t.setAmount(dto.getAmount());
        t.setType(dto.getType());
        t.setCategory(dto.getCategory());
        t.setDate(dto.getDate());
        t.setNotes(dto.getNotes());

        return toDto(repo.save(t));
    }


    @PreAuthorize("hasRole('ADMIN')")
    public String delete(Long id) {

        Transaction t = repo.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));

        t.setDeleted(true);
        repo.save(t);

        return "Transaction deleted successfully";
    }


    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public List<TransactionResponseDto> filter(
            TransactionType type,
            String category,
            LocalDate from,
            LocalDate to) {

        User user = getCurrentUser();

        List<Transaction> transactions;

        if (user.getRole() == Role.ADMIN || user.getRole() == Role.ANALYST) {
            transactions = repo.findAllByDeletedFalse();
        } else {
            transactions = repo.findByCreatedByAndDeletedFalse(user);
        }

        return transactions.stream()
                .filter(t -> type == null || t.getType() == type)
                .filter(t -> category == null || t.getCategory().equalsIgnoreCase(category))
                .filter(t -> from == null || !t.getDate().isBefore(from))
                .filter(t -> to == null || !t.getDate().isAfter(to))
                .map(this::toDto)
                .toList();
    }



    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public Double getTotalIncome() {
        User user = getCurrentUser();

        if (user.getRole() == Role.ADMIN || user.getRole() == Role.ANALYST) {
            return repo.getTotalIncome();
        }

        return repo.getUserIncome(user.getId());
    }

    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public Double getTotalExpense() {
        User user = getCurrentUser();

        if (user.getRole() == Role.ADMIN || user.getRole() == Role.ANALYST) {
            return repo.getTotalExpense();
        }

        return repo.getUserExpense(user.getId());
    }

    @PreAuthorize("hasAnyRole('VIEWER','ANALYST','ADMIN')")
    public List<TransactionResponseDto> getRecentTransactions() {

        User user = getCurrentUser();

        List<Transaction> list;

        if (user.getRole() == Role.ADMIN || user.getRole() == Role.ANALYST) {
            list = repo.findTop5ByDeletedFalseOrderByDateDesc();
        } else {
            list = repo.findTop5ByCreatedByAndDeletedFalseOrderByDateDesc(user);
        }

        return list.stream().map(this::toDto).toList();
    }


    private TransactionResponseDto toDto(Transaction t) {
        return TransactionResponseDto.builder()
                .id(t.getId())
                .amount(t.getAmount())
                .type(t.getType())
                .category(t.getCategory())
                .date(t.getDate())
                .notes(t.getNotes())
                .createdBy(t.getCreatedBy().getId())
                .build();
    }
}
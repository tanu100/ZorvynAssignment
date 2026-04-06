package com.zorvyn.Modules.Report.Services;

import com.zorvyn.Modules.Report.DTOs.TransactionDto;
import com.zorvyn.Modules.Report.DTOs.TransactionResponseDto;
import com.zorvyn.Modules.Report.Exception.TransactionNotFoundException;
import com.zorvyn.Modules.Report.Exception.UnauthorizedActionException;
import com.zorvyn.Modules.Report.Models.Transaction;
import com.zorvyn.Modules.Report.Models.TransactionType;
import com.zorvyn.Modules.Report.Repository.TransactionRepository;
import com.zorvyn.Modules.User.Models.Role;
import com.zorvyn.Modules.User.Models.User;
import com.zorvyn.Modules.User.Services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repo;
    private final UserServices userServices;

    private void requireAdmin(Long userId) {
        User user = userServices.FindById(userId);
        if (user.getRole() != Role.ADMIN) {
            throw new UnauthorizedActionException(
                    "Access denied. Only ADMIN can perform this action.");
        }
    }

    private void requireActiveUser(Long userId) {
        User user = userServices.FindById(userId);
        if (user.getStatus() != com.zorvyn.Modules.User.Models.Status.ACTIVE) {
            throw new UnauthorizedActionException(
                    "Access denied. Your account is inactive.");
        }
    }

    public TransactionResponseDto create(Long userId, TransactionDto dto) {
        requireAdmin(userId);

        Transaction t = new Transaction();
        t.setAmount(dto.getAmount());
        t.setType(dto.getType());
        t.setCategory(dto.getCategory());
        t.setDate(dto.getDate());
        t.setNotes(dto.getNotes());

        return toDto(repo.save(t));
    }

    public List<TransactionResponseDto> getAll(Long userId) {
        requireActiveUser(userId);
        return repo.findAllByDeletedFalse()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TransactionResponseDto getById(Long userId, Long transactionId) {
        requireActiveUser(userId);
        Transaction t = repo.findByIdAndDeletedFalse(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));
        return toDto(t);
    }

    public TransactionResponseDto update(Long userId, Long transactionId, TransactionDto dto) {
        requireAdmin(userId);

        Transaction t = repo.findByIdAndDeletedFalse(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));

        t.setAmount(dto.getAmount());
        t.setType(dto.getType());
        t.setCategory(dto.getCategory());
        t.setDate(dto.getDate());
        t.setNotes(dto.getNotes());

        return toDto(repo.save(t));
    }

    public String delete(Long userId, Long transactionId) {
        requireAdmin(userId);

        Transaction t = repo.findByIdAndDeletedFalse(transactionId)
                .orElseThrow(() -> new TransactionNotFoundException(transactionId));

        t.setDeleted(true);
        repo.save(t);

        return "Transaction with id " + transactionId + " has been deleted.";
    }

    public List<TransactionResponseDto> filter(
            Long userId,
            TransactionType type,
            String category,
            LocalDate from,
            LocalDate to) {

        requireActiveUser(userId);

        List<Transaction> results;

        boolean hasType     = type != null;
        boolean hasCategory = category != null && !category.isBlank();
        boolean hasDateRange = from != null && to != null;

        if (hasType && hasCategory && hasDateRange) {
            results = repo.findAllByTypeAndCategoryIgnoreCaseAndDateBetweenAndDeletedFalse(type, category, from, to);
        } else if (hasType && hasCategory) {
            results = repo.findAllByTypeAndCategoryIgnoreCaseAndDeletedFalse(type, category);
        } else if (hasType && hasDateRange) {
            results = repo.findAllByTypeAndDateBetweenAndDeletedFalse(type, from, to);
        } else if (hasCategory && hasDateRange) {
            results = repo.findAllByCategoryIgnoreCaseAndDateBetweenAndDeletedFalse(category, from, to);
        } else if (hasType) {
            results = repo.findAllByTypeAndDeletedFalse(type);
        } else if (hasCategory) {
            results = repo.findAllByCategoryIgnoreCaseAndDeletedFalse(category);
        } else if (hasDateRange) {
            results = repo.findAllByDateBetweenAndDeletedFalse(from, to);
        } else {
            results = repo.findAllByDeletedFalse();
        }

        return results.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private TransactionResponseDto toDto(Transaction t) {
        return TransactionResponseDto.builder()
                .id(t.getId())
                .amount(t.getAmount())
                .type(t.getType())
                .category(t.getCategory())
                .date(t.getDate())
                .notes(t.getNotes())
                .build();
    }
}
package com.zorvyn.Modules.Report.Exception;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(Long id) {
        super("Transaction not found with id: " + id);
    }
}
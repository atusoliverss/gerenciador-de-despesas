package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.ExpenseRepository;
import java.util.UUID;

public class DeleteExpenseUseCase {
    private final ExpenseRepository expenseRepository;

    public DeleteExpenseUseCase(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public void execute(UUID id) {
        expenseRepository.deleteById(id);
    }
}
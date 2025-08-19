package com.atus.gerdp.core.application.usecases.expense;

import com.atus.gerdp.core.application.repositories.ExpenseRepository;
import java.util.UUID;

/**
 * Caso de Uso para deletar uma despesa.
 */
public class DeleteExpenseUseCase {
    private final ExpenseRepository expenseRepository;

    public DeleteExpenseUseCase(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * Executa a lógica de deleção.
     */
    public void execute(UUID id) {
        expenseRepository.deleteById(id);
    }
}
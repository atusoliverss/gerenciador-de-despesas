package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import java.util.UUID;

public class DeleteBudgetUseCase {
    private final BudgetRepository budgetRepository;

    public DeleteBudgetUseCase(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public void execute(UUID id) {
        // Valida se o orçamento existe antes de deletar
        budgetRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Budget not found with id: " + id));
        budgetRepository.deleteById(id);
    }
}
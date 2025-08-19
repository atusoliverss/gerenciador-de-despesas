package com.atus.gerdp.core.application.usecases.budget;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import java.util.UUID;

/**
 * Caso de Uso para deletar um orçamento.
 */
public class DeleteBudgetUseCase {
    private final BudgetRepository budgetRepository;

    public DeleteBudgetUseCase(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    /**
     * Executa a lógica de deleção.
     */
    public void execute(UUID id) {
        // Valida se o orçamento existe antes de tentar deletar
        budgetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Budget not found with id: " + id));
        
        // Deleta o orçamento
        budgetRepository.deleteById(id);
    }
}
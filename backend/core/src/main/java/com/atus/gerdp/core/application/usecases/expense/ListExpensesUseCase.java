package com.atus.gerdp.core.application.usecases.expense;

import com.atus.gerdp.core.application.repositories.ExpenseRepository;
import com.atus.gerdp.core.domain.entities.Expense;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Caso de Uso para listar todas as despesas.
 */
public class ListExpensesUseCase {
    private final ExpenseRepository expenseRepository;

    public ListExpensesUseCase(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * Executa a busca por todas as despesas.
     */
    public List<Output> execute() {
        return expenseRepository.findAll().stream()
                .map(Output::from)
                .collect(Collectors.toList());
    }

    // Dados de saída do caso de uso
    public record Output(UUID id, String description, BigDecimal amount, LocalDate date, UUID categoryId) {
        public static Output from(Expense expense) {
            return new Output(expense.getId(), expense.getDescription(), expense.getAmount(), expense.getDate(), expense.getCategoryId());
        }
    }
}
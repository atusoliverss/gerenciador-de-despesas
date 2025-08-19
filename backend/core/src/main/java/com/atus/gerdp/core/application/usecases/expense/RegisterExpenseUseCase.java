package com.atus.gerdp.core.application.usecases.expense;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.application.repositories.ExpenseRepository;
import com.atus.gerdp.core.domain.entities.Expense;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Caso de Uso para registrar uma nova despesa.
 */
public class RegisterExpenseUseCase {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public RegisterExpenseUseCase(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Executa a lógica de validação e criação da despesa.
     */
    public Output execute(Input input) {
        // Valida se o valor da despesa é positivo
        if (input.amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive.");
        }
        // Valida se a categoria informada existe
        categoryRepository.findById(input.categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found."));
        
        var newExpense = Expense.builder()
                .id(UUID.randomUUID())
                .description(input.description)
                .amount(input.amount)
                .date(input.date)
                .categoryId(input.categoryId)
                .build();
        
        var savedExpense = expenseRepository.save(newExpense);
        
        return Output.from(savedExpense);
    }

    // Dados de entrada para o caso de uso
    public record Input(String description, BigDecimal amount, LocalDate date, UUID categoryId) {}

    // Dados de saída do caso de uso
    public record Output(UUID id, String description, BigDecimal amount, LocalDate date, UUID categoryId) {
        public static Output from(Expense expense) {
            return new Output(expense.getId(), expense.getDescription(), expense.getAmount(), expense.getDate(), expense.getCategoryId());
        }
    }
}
package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.application.repositories.ExpenseRepository;
import com.atus.gerdp.core.domain.entities.Expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class UpdateExpenseUseCase {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public UpdateExpenseUseCase(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    public void execute(Input input) {
        // Validação 1: O valor deve ser positivo
        if (input.amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive.");
        }
        // Validação 2: A categoria deve existir
        categoryRepository.findById(input.categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + input.categoryId()));

        // Busca a despesa existente
        Expense expense = expenseRepository.findById(input.id())
                .orElseThrow(() -> new IllegalArgumentException("Expense not found with id: " + input.id()));

        // Atualiza todos os campos
        expense.setDescription(input.description());
        expense.setAmount(input.amount());
        expense.setDate(input.date());
        expense.setCategoryId(input.categoryId());

        // Salva a entidade atualizada
        expenseRepository.save(expense);
    }

    public record Input(
            UUID id,
            String description,
            BigDecimal amount,
            LocalDate date,
            UUID categoryId
    ) {}
}
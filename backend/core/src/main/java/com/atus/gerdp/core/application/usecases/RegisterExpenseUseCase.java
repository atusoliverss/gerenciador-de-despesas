package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.application.repositories.ExpenseRepository;
import com.atus.gerdp.core.domain.entities.Expense;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class RegisterExpenseUseCase {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    public RegisterExpenseUseCase(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    public Output execute(Input input) {
        if (input.amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive.");
        }
        categoryRepository.findById(input.categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found."));
        var newExpense = Expense.builder().id(UUID.randomUUID()).description(input.description).amount(input.amount)
                .date(input.date).categoryId(input.categoryId).build();
        var savedExpense = expenseRepository.save(newExpense);
        return Output.from(savedExpense);
    }

    public record Input(String description, BigDecimal amount, LocalDate date, UUID categoryId) {
    }

    public record Output(UUID id, String description, BigDecimal amount, LocalDate date, UUID categoryId) {
        public static Output from(Expense expense) {
            return new Output(expense.getId(), expense.getDescription(), expense.getAmount(), expense.getDate(),
                    expense.getCategoryId());
        }
    }
}
package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.ExpenseRepository;
import com.atus.gerdp.core.domain.entities.Expense;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ListExpensesUseCase {
    private final ExpenseRepository expenseRepository;

    public ListExpensesUseCase(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Output> execute() {
        return expenseRepository.findAll().stream().map(Output::from).collect(Collectors.toList());
    }

    public record Output(UUID id, String description, BigDecimal amount, LocalDate date, UUID categoryId) {
        public static Output from(Expense expense) {
            return new Output(expense.getId(), expense.getDescription(), expense.getAmount(), expense.getDate(),
                    expense.getCategoryId());
        }
    }
}
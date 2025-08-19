package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.domain.entities.Budget;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

public class CreateOrUpdateBudgetUseCase {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public CreateOrUpdateBudgetUseCase(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    public Output execute(Input input) {
        categoryRepository.findById(input.categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found."));
        var existingBudgetOpt = budgetRepository.findByCategoryIdAndPeriod(input.categoryId(), input.period());
        Budget budgetToSave;
        if (existingBudgetOpt.isPresent()) {
            var existingBudget = existingBudgetOpt.get();
            existingBudget.setAmount(input.amount());
            budgetToSave = existingBudget;
        } else {
            budgetToSave = Budget.builder().id(UUID.randomUUID()).amount(input.amount()).period(input.period())
                    .categoryId(input.categoryId()).build();
        }
        var savedBudget = budgetRepository.save(budgetToSave);
        return new Output(savedBudget.getId(), savedBudget.getAmount(), savedBudget.getPeriod(),
                savedBudget.getCategoryId());
    }

    public record Input(BigDecimal amount, YearMonth period, UUID categoryId) {
    }

    public record Output(UUID id, BigDecimal amount, YearMonth period, UUID categoryId) {
    }
}
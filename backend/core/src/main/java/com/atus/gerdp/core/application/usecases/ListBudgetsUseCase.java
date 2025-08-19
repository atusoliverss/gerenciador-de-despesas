package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import com.atus.gerdp.core.application.repositories.CategoryRepository;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ListBudgetsUseCase {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public ListBudgetsUseCase(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Output> execute(Input input) {
        var budgets = budgetRepository.findByPeriod(input.period());
        
        return budgets.stream().map(budget -> {
            var categoryName = categoryRepository.findById(budget.getCategoryId())
                    .map(c -> c.getName())
                    .orElse("Categoria Desconhecida");
            
            return new Output(budget.getId(), budget.getAmount(), budget.getPeriod(), budget.getCategoryId(), categoryName);
        }).collect(Collectors.toList());
    }

    public record Input(YearMonth period) {}
    public record Output(UUID id, BigDecimal amount, YearMonth period, UUID categoryId, String categoryName) {}
}
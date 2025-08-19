package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.application.repositories.ExpenseRepository;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetDashboardSummaryUseCase {
    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public GetDashboardSummaryUseCase(ExpenseRepository er, BudgetRepository br, CategoryRepository cr) {
        this.expenseRepository = er;
        this.budgetRepository = br;
        this.categoryRepository = cr;
    }

    public DashboardOutput execute(Input input) {
        var expenses = expenseRepository.findByPeriod(input.period());
        var budgets = budgetRepository.findByPeriod(input.period());
        var categories = categoryRepository.findAll();

        // Calcula o total de despesas do mês
        BigDecimal totalExpenses = expenses.stream()
                .map(e -> e.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcula o total orçado para o mês
        BigDecimal totalBudget = budgets.stream()
                .map(b -> b.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Agrupa despesas por ID de categoria
        Map<UUID, BigDecimal> expensesByCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        e -> e.getCategoryId(),
                        Collectors.reducing(BigDecimal.ZERO, e -> e.getAmount(), BigDecimal::add)
                ));

        // Agrupa orçamentos por ID de categoria
        Map<UUID, BigDecimal> budgetsByCategory = budgets.stream()
                .collect(Collectors.toMap(b -> b.getCategoryId(), b -> b.getAmount()));

        // Monta o resumo detalhado por categoria
        List<CategorySpending> spendingByCategory = categories.stream().map(category -> {
            BigDecimal spent = expensesByCategory.getOrDefault(category.getId(), BigDecimal.ZERO);
            BigDecimal budgeted = budgetsByCategory.getOrDefault(category.getId(), BigDecimal.ZERO);
            return new CategorySpending(category.getId(), category.getName(), spent, budgeted, budgeted.subtract(spent));
        }).toList();


        return new DashboardOutput(
                input.period(),
                totalExpenses,
                totalBudget,
                totalBudget.subtract(totalExpenses),
                spendingByCategory
        );
    }

    public record Input(YearMonth period) {}

    public record DashboardOutput(
            YearMonth period,
            BigDecimal totalExpenses,
            BigDecimal totalBudget,
            BigDecimal difference,
            List<CategorySpending> spendingByCategory
    ) {}

    public record CategorySpending(
            UUID categoryId,
            String categoryName,
            BigDecimal amountSpent,
            BigDecimal amountBudgeted,
            BigDecimal difference
    ) {}
}
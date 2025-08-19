package com.atus.gerdp.core.application.usecases.dashboard;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.application.repositories.ExpenseRepository;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Caso de Uso para buscar e calcular os dados de resumo para o Dashboard.
 */
public class GetDashboardSummaryUseCase {
    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public GetDashboardSummaryUseCase(ExpenseRepository expenseRepository, BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Executa a busca e a consolidação dos dados do dashboard.
     */
    public DashboardOutput execute(Input input) {
        var expenses = expenseRepository.findByPeriod(input.period());
        var budgets = budgetRepository.findByPeriod(input.period());
        var categories = categoryRepository.findAll();

        // Calcula o total de despesas do mês
        BigDecimal totalExpenses = expenses.stream()
                .map(expense -> expense.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcula o total orçado para o mês
        BigDecimal totalBudget = budgets.stream()
                .map(budget -> budget.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Agrupa despesas por ID de categoria para somar os gastos
        Map<UUID, BigDecimal> expensesByCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        expense -> expense.getCategoryId(),
                        Collectors.reducing(BigDecimal.ZERO, expense -> expense.getAmount(), BigDecimal::add)
                ));

        // Agrupa orçamentos por ID de categoria
        Map<UUID, BigDecimal> budgetsByCategory = budgets.stream()
                .collect(Collectors.toMap(budget -> budget.getCategoryId(), budget -> budget.getAmount()));

        // Monta o resumo detalhado para cada categoria
        List<CategorySpending> spendingByCategory = categories.stream().map(category -> {
            BigDecimal spent = expensesByCategory.getOrDefault(category.getId(), BigDecimal.ZERO);
            BigDecimal budgeted = budgetsByCategory.getOrDefault(category.getId(), BigDecimal.ZERO);
            return new CategorySpending(category.getId(), category.getName(), spent, budgeted, budgeted.subtract(spent));
        }).toList();

        // Retorna o objeto de saída completo com todos os dados calculados
        return new DashboardOutput(
                input.period(),
                totalExpenses,
                totalBudget,
                totalBudget.subtract(totalExpenses),
                spendingByCategory
        );
    }

    // Dados de entrada para o caso de uso
    public record Input(YearMonth period) {}

    // Dados de saída do caso de uso
    public record DashboardOutput(
            YearMonth period,
            BigDecimal totalExpenses,
            BigDecimal totalBudget,
            BigDecimal difference,
            List<CategorySpending> spendingByCategory
    ) {}

    // Objeto que representa os gastos de uma única categoria
    public record CategorySpending(
            UUID categoryId,
            String categoryName,
            BigDecimal amountSpent,
            BigDecimal amountBudgeted,
            BigDecimal difference
    ) {}
}
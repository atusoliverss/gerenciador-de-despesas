package com.atus.gerdp.core.application.usecases.budget;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import com.atus.gerdp.core.application.repositories.CategoryRepository;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Caso de Uso para listar os orçamentos de um mês específico.
 */
public class ListBudgetsUseCase {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public ListBudgetsUseCase(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Executa a busca e enriquece os dados com o nome da categoria.
     */
    public List<Output> execute(Input input) {
        var budgets = budgetRepository.findByPeriod(input.period());
        
        // Para cada orçamento encontrado, busca o nome da sua categoria
        return budgets.stream().map(budget -> {
            var categoryName = categoryRepository.findById(budget.getCategoryId())
                    .map(category -> category.getName())
                    .orElse("Categoria Desconhecida");
            
            return new Output(budget.getId(), budget.getAmount(), budget.getPeriod(), budget.getCategoryId(), categoryName);
        }).collect(Collectors.toList());
    }

    // Dados de entrada para o caso de uso
    public record Input(YearMonth period) {}

    // Dados de saída do caso de uso (incluindo o nome da categoria)
    public record Output(UUID id, BigDecimal amount, YearMonth period, UUID categoryId, String categoryName) {}
}
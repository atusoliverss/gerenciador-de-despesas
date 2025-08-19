package com.atus.gerdp.core.application.usecases.budget;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.domain.entities.Budget;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

/**
 * Caso de Uso para criar um novo orçamento ou atualizar um já existente.
 */
public class CreateOrUpdateBudgetUseCase {
    private final BudgetRepository budgetRepository;
    private final CategoryRepository categoryRepository;

    public CreateOrUpdateBudgetUseCase(BudgetRepository budgetRepository, CategoryRepository categoryRepository) {
        this.budgetRepository = budgetRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Executa a lógica de criar ou atualizar.
     */
    public Output execute(Input input) {
        // Valida se a categoria informada existe
        categoryRepository.findById(input.categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found."));

        // Procura se já existe um orçamento para essa categoria e mês
        var existingBudgetOpt = budgetRepository.findByCategoryIdAndPeriod(input.categoryId(), input.period());

        Budget budgetToSave;
        if (existingBudgetOpt.isPresent()) {
            // Se já existe, apenas atualiza o valor
            var existingBudget = existingBudgetOpt.get();
            existingBudget.setAmount(input.amount());
            budgetToSave = existingBudget;
        } else {
            // Se não existe, cria um novo orçamento
            budgetToSave = Budget.builder()
                    .id(UUID.randomUUID())
                    .amount(input.amount())
                    .period(input.period())
                    .categoryId(input.categoryId())
                    .build();
        }

        // Salva o orçamento (novo ou atualizado)
        var savedBudget = budgetRepository.save(budgetToSave);

        return new Output(savedBudget.getId(), savedBudget.getAmount(), savedBudget.getPeriod(), savedBudget.getCategoryId());
    }

    // Dados de entrada para o caso de uso
    public record Input(BigDecimal amount, YearMonth period, UUID categoryId) {}

    // Dados de saída do caso de uso
    public record Output(UUID id, BigDecimal amount, YearMonth period, UUID categoryId) {}
}
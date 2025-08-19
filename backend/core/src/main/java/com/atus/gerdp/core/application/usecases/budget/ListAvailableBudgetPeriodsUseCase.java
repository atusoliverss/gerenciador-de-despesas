package com.atus.gerdp.core.application.usecases.budget;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import java.time.YearMonth;
import java.util.List;

/**
 * Caso de Uso para listar os meses que possuem orçamentos cadastrados.
 */
public class ListAvailableBudgetPeriodsUseCase {
    private final BudgetRepository budgetRepository;

    public ListAvailableBudgetPeriodsUseCase(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    /**
     * Executa a busca pelos meses.
     */
    public List<YearMonth> execute() {
        return budgetRepository.findDistinctPeriods();
    }
}
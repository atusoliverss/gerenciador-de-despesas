package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import java.time.YearMonth;
import java.util.List;

public class ListAvailableBudgetPeriodsUseCase {
    private final BudgetRepository budgetRepository;

    public ListAvailableBudgetPeriodsUseCase(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public List<YearMonth> execute() {
        return budgetRepository.findDistinctPeriods();
    }
}
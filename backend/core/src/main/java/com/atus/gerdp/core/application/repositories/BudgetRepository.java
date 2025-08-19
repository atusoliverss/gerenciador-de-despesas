package com.atus.gerdp.core.application.repositories;

import com.atus.gerdp.core.domain.entities.Budget;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository {

    Budget save(Budget budget);

    Optional<Budget> findByCategoryIdAndPeriod(UUID categoryId, YearMonth period);

    List<Budget> findByPeriod(YearMonth period);

    List<YearMonth> findDistinctPeriods();

    void deleteById(UUID id);

    Optional<Budget> findById(UUID id);

}
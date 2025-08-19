package com.atus.gerdp.core.application.repositories;

import com.atus.gerdp.core.domain.entities.Expense;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExpenseRepository {

    Expense save(Expense expense);

    List<Expense> findByPeriod(YearMonth period);

    List<Expense> findAll();

    void deleteById(UUID id);

    Optional<Expense> findById(UUID id);
}
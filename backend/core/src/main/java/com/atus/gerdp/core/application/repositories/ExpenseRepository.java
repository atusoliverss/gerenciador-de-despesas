package com.atus.gerdp.core.application.repositories;

import com.atus.gerdp.core.domain.entities.Expense;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define as operações de banco de dados para as Despesas (Expenses).
 * É um "contrato" que a camada de infraestrutura deve implementar.
 */
public interface ExpenseRepository {

    /**
     * Salva ou atualiza uma despesa.
     */
    Expense save(Expense expense);

    /**
     * Lista todas as despesas de um mês específico.
     */
    List<Expense> findByPeriod(YearMonth period);

    /**
     * Lista todas as despesas.
     */
    List<Expense> findAll();

    /**
     * Deleta uma despesa pelo seu ID.
     */
    void deleteById(UUID id);

    /**
     * Busca uma despesa pelo seu ID.
     */
    Optional<Expense> findById(UUID id);
}

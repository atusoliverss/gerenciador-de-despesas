package com.atus.gerdp.core.application.repositories;

import com.atus.gerdp.core.domain.entities.Budget;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define as operações de banco de dados para os Orçamentos (Budgets).
 * É um "contrato" que a camada de infraestrutura deve implementar.
 */
public interface BudgetRepository {

    /**
     * Salva ou atualiza um orçamento.
     */
    Budget save(Budget budget);

    /**
     * Busca um orçamento por categoria e mês.
     */
    Optional<Budget> findByCategoryIdAndPeriod(UUID categoryId, YearMonth period);

    /**
     * Lista todos os orçamentos de um mês.
     */
    List<Budget> findByPeriod(YearMonth period);

    /**
     * Lista os meses que têm orçamentos cadastrados, sem repetição.
     */
    List<YearMonth> findDistinctPeriods();

    /**
     * Deleta um orçamento pelo seu ID.
     */
    void deleteById(UUID id);

    /**
     * Busca um orçamento pelo seu ID.
     */
    Optional<Budget> findById(UUID id);

}

package com.atus.gerdp.infrastructure.persistence.jpa.repositories;

import com.atus.gerdp.infrastructure.persistence.jpa.entities.ExpenseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Interface do Spring Data JPA para a entidade Expense.
 * Fornece os métodos CRUD básicos e um método de busca customizado.
 */
@Repository
public interface SpringDataExpenseRepository extends JpaRepository<ExpenseJpaEntity, UUID> {
    
    /**
     * Busca todas as despesas que ocorreram entre uma data inicial e uma data final.
     * O Spring cria a query automaticamente a partir do nome do método.
     */
    List<ExpenseJpaEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
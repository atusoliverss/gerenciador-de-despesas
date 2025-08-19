package com.atus.gerdp.infrastructure.persistence.jpa.repositories;

import com.atus.gerdp.infrastructure.persistence.jpa.entities.BudgetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interface do Spring Data JPA para a entidade Budget.
 * Ao estender JpaRepository, o Spring cria automaticamente os métodos básicos
 * de CRUD (save, findById, findAll, deleteById, etc.).
 */
@Repository
public interface SpringDataBudgetRepository extends JpaRepository<BudgetJpaEntity, UUID> {
    
    /**
     * Busca um orçamento por ID da categoria e período (mês/ano).
     * O Spring cria a query automaticamente a partir do nome do método.
     */
    Optional<BudgetJpaEntity> findByCategory_IdAndPeriod(UUID categoryId, YearMonth period);
    
    /**
     * Busca todos os orçamentos de um determinado período.
     */
    List<BudgetJpaEntity> findByPeriod(YearMonth period);

    /**
     * Query customizada para buscar todos os meses distintos que têm orçamentos.
     * @Query permite escrever a consulta manualmente em JPQL.
     */
    @Query("SELECT DISTINCT b.period FROM BudgetJpaEntity b ORDER BY b.period DESC")
    List<YearMonth> findDistinctPeriods();
}
package com.atus.gerdp.infrastructure.persistence.mappers;

import com.atus.gerdp.core.domain.entities.Budget;
import com.atus.gerdp.infrastructure.persistence.jpa.entities.BudgetJpaEntity;
import com.atus.gerdp.infrastructure.persistence.jpa.entities.CategoryJpaEntity;

/**
 * Converte objetos Budget entre a camada de domínio (core) e a de persistência (JPA).
 */
public class BudgetPersistenceMapper {
    /**
     * Converte uma entidade JPA (do banco) para uma entidade de domínio (da aplicação).
     */
    public Budget toDomain(BudgetJpaEntity jpaEntity) {
        return new Budget(
                jpaEntity.getId(),
                jpaEntity.getAmount(),
                jpaEntity.getPeriod(),
                jpaEntity.getCategory().getId()
        );
    }

    /**
     * Converte uma entidade de domínio (da aplicação) para uma entidade JPA (para o banco).
     */
    public BudgetJpaEntity toJpaEntity(Budget domainEntity) {
        var categoryEntity = new CategoryJpaEntity();
        categoryEntity.setId(domainEntity.getCategoryId());

        return new BudgetJpaEntity(
                domainEntity.getId(),
                domainEntity.getAmount(),
                domainEntity.getPeriod(),
                categoryEntity
        );
    }
}
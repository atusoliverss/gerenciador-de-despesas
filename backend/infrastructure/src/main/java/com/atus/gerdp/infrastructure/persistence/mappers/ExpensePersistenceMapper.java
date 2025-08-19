package com.atus.gerdp.infrastructure.persistence.mappers;

import com.atus.gerdp.core.domain.entities.Expense;
import com.atus.gerdp.infrastructure.persistence.jpa.entities.CategoryJpaEntity;
import com.atus.gerdp.infrastructure.persistence.jpa.entities.ExpenseJpaEntity;

/**
 * Converte objetos Expense entre a camada de domínio (core) e a de persistência (JPA).
 */
public class ExpensePersistenceMapper {
    /**
     * Converte uma entidade JPA (do banco) para uma entidade de domínio (da aplicação).
     */
    public Expense toDomain(ExpenseJpaEntity jpaEntity) {
        return new Expense(
                jpaEntity.getId(),
                jpaEntity.getDescription(),
                jpaEntity.getAmount(),
                jpaEntity.getDate(),
                jpaEntity.getCategory().getId()
        );
    }

    /**
     * Converte uma entidade de domínio (da aplicação) para uma entidade JPA (para o banco).
     */
    public ExpenseJpaEntity toJpaEntity(Expense domainEntity) {
        var categoryEntity = new CategoryJpaEntity();
        categoryEntity.setId(domainEntity.getCategoryId());
        
        return new ExpenseJpaEntity(
                domainEntity.getId(),
                domainEntity.getDescription(),
                domainEntity.getAmount(),
                domainEntity.getDate(),
                categoryEntity
        );
    }
}
package com.atus.gerdp.infrastructure.persistence.mappers;

import com.atus.gerdp.core.domain.entities.Category;
import com.atus.gerdp.infrastructure.persistence.jpa.entities.CategoryJpaEntity;

/**
 * Converte objetos Category entre a camada de domínio (core) e a de persistência (JPA).
 */
public class CategoryPersistenceMapper {
    /**
     * Converte uma entidade JPA (do banco) para uma entidade de domínio (da aplicação).
     */
    public Category toDomain(CategoryJpaEntity jpaEntity) {
        return new Category(jpaEntity.getId(), jpaEntity.getName());
    }

    /**
     * Converte uma entidade de domínio (da aplicação) para uma entidade JPA (para o banco).
     */
    public CategoryJpaEntity toJpaEntity(Category domainEntity) {
        return new CategoryJpaEntity(domainEntity.getId(), domainEntity.getName());
    }
}
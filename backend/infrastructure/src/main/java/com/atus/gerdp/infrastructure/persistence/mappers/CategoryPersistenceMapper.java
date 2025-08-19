package com.atus.gerdp.infrastructure.persistence.mappers;

import com.atus.gerdp.core.domain.entities.Category;
import com.atus.gerdp.infrastructure.persistence.jpa.entities.CategoryJpaEntity;

public class CategoryPersistenceMapper {
    public Category toDomain(CategoryJpaEntity j) {
        return new Category(j.getId(), j.getName());
    }

    public CategoryJpaEntity toJpaEntity(Category d) {
        return new CategoryJpaEntity(d.getId(), d.getName());
    }
}
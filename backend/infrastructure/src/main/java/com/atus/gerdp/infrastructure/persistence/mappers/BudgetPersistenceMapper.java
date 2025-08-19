package com.atus.gerdp.infrastructure.persistence.mappers;

import com.atus.gerdp.core.domain.entities.Budget;
import com.atus.gerdp.infrastructure.persistence.jpa.entities.BudgetJpaEntity;
import com.atus.gerdp.infrastructure.persistence.jpa.entities.CategoryJpaEntity;

public class BudgetPersistenceMapper {
    public Budget toDomain(BudgetJpaEntity j) {
        return new Budget(j.getId(),
                j.getAmount(),
                j.getPeriod(),
                j.getCategory().getId());
    }

    public BudgetJpaEntity toJpaEntity(Budget d) {
        var c = new CategoryJpaEntity();
        c.setId(d.getCategoryId());

        return new BudgetJpaEntity(d.getId(),
                d.getAmount(),
                d.getPeriod(),
                c);
    }
}
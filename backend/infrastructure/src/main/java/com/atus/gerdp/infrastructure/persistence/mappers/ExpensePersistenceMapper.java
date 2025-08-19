package com.atus.gerdp.infrastructure.persistence.mappers;

import com.atus.gerdp.core.domain.entities.Expense;
import com.atus.gerdp.infrastructure.persistence.jpa.entities.CategoryJpaEntity;
import com.atus.gerdp.infrastructure.persistence.jpa.entities.ExpenseJpaEntity;

public class ExpensePersistenceMapper {
    public Expense toDomain(ExpenseJpaEntity j) {
        return new Expense(j.getId(),
                j.getDescription(),
                j.getAmount(),
                j.getDate(),
                j.getCategory().getId());
    }

    public ExpenseJpaEntity toJpaEntity(Expense d) {
        var c = new CategoryJpaEntity();
        c.setId(d.getCategoryId());
        return new ExpenseJpaEntity(d.getId(),
                d.getDescription(),
                d.getAmount(),
                d.getDate(),
                c);
    }
}
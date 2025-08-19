package com.atus.gerdp.infrastructure.persistence.repositories;

import com.atus.gerdp.core.application.repositories.ExpenseRepository;
import com.atus.gerdp.core.domain.entities.Expense;
import com.atus.gerdp.infrastructure.persistence.jpa.repositories.SpringDataExpenseRepository;
import com.atus.gerdp.infrastructure.persistence.mappers.ExpensePersistenceMapper;
import org.springframework.stereotype.Component;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final SpringDataExpenseRepository r;
    private final ExpensePersistenceMapper m = new ExpensePersistenceMapper();

    public ExpenseRepositoryImpl(SpringDataExpenseRepository r) {
        this.r = r;
    }

    @Override
    public Expense save(Expense e) {
        var j = m.toJpaEntity(e);
        var s = r.save(j);
        return m.toDomain(s);
    }

    @Override
    public List<Expense> findByPeriod(YearMonth p) {
        return r.findByDateBetween(p.atDay(1), p.atEndOfMonth()).stream().map(m::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Expense> findAll() {
        return r.findAll().stream().map(m::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        r.deleteById(id);
    }

    @Override
    public Optional<Expense> findById(UUID id) {
        return r.findById(id)
                .map(m::toDomain);
    }
}
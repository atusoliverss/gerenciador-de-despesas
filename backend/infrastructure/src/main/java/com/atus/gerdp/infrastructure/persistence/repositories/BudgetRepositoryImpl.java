package com.atus.gerdp.infrastructure.persistence.repositories;

import com.atus.gerdp.core.application.repositories.BudgetRepository;
import com.atus.gerdp.core.domain.entities.Budget;
import com.atus.gerdp.infrastructure.persistence.jpa.repositories.SpringDataBudgetRepository;
import com.atus.gerdp.infrastructure.persistence.mappers.BudgetPersistenceMapper;
import org.springframework.stereotype.Component;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BudgetRepositoryImpl implements BudgetRepository {
    private final SpringDataBudgetRepository r;
    private final BudgetPersistenceMapper m = new BudgetPersistenceMapper();

    public BudgetRepositoryImpl(SpringDataBudgetRepository r) {
        this.r = r;
    }

    @Override
    public Budget save(Budget b) {
        var j = m.toJpaEntity(b);
        var s = r.save(j);
        return m.toDomain(s);
    }

    @Override
    public Optional<Budget> findByCategoryIdAndPeriod(UUID c, YearMonth p) {
        return r.findByCategory_IdAndPeriod(c, p).map(m::toDomain);
    }

    @Override
    public List<Budget> findByPeriod(YearMonth period) {
        return r.findByPeriod(period).stream()
                .map(m::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        r.deleteById(id);
    }

    @Override
    public Optional<Budget> findById(UUID id) {
        return r.findById(id)
                .map(m::toDomain);
    }

    @Override
    public List<YearMonth> findDistinctPeriods() {
        return r.findDistinctPeriods();
    }
}
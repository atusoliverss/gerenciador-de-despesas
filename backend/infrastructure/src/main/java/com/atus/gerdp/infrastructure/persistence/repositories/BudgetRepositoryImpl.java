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

/**
 * Implementação do contrato BudgetRepository.
 * Usa o Spring Data JPA para de fato interagir com o banco de dados.
 */
@Component
public class BudgetRepositoryImpl implements BudgetRepository {
    private final SpringDataBudgetRepository springDataRepository;
    private final BudgetPersistenceMapper mapper = new BudgetPersistenceMapper();

    public BudgetRepositoryImpl(SpringDataBudgetRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Budget save(Budget budget) {
        var jpaEntity = mapper.toJpaEntity(budget);
        var savedEntity = springDataRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Budget> findByCategoryIdAndPeriod(UUID categoryId, YearMonth period) {
        return springDataRepository.findByCategory_IdAndPeriod(categoryId, period).map(mapper::toDomain);
    }

    @Override
    public List<Budget> findByPeriod(YearMonth period) {
        return springDataRepository.findByPeriod(period).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public Optional<Budget> findById(UUID id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<YearMonth> findDistinctPeriods() {
        return springDataRepository.findDistinctPeriods();
    }
}
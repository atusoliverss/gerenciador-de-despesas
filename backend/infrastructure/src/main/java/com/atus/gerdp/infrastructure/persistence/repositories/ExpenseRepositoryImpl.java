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

/**
 * Implementação do contrato ExpenseRepository.
 * Usa o Spring Data JPA para de fato interagir com o banco de dados.
 */
@Component
public class ExpenseRepositoryImpl implements ExpenseRepository {
    private final SpringDataExpenseRepository springDataRepository;
    private final ExpensePersistenceMapper mapper = new ExpensePersistenceMapper();

    public ExpenseRepositoryImpl(SpringDataExpenseRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Expense save(Expense expense) {
        var jpaEntity = mapper.toJpaEntity(expense);
        var savedEntity = springDataRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<Expense> findByPeriod(YearMonth period) {
        var startDate = period.atDay(1);
        var endDate = period.atEndOfMonth();
        return springDataRepository.findByDateBetween(startDate, endDate).stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Expense> findAll() {
        return springDataRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        springDataRepository.deleteById(id);
    }

    @Override
    public Optional<Expense> findById(UUID id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }
}
package com.atus.gerdp.infrastructure.persistence.jpa.repositories;

import com.atus.gerdp.infrastructure.persistence.jpa.entities.ExpenseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataExpenseRepository extends JpaRepository<ExpenseJpaEntity, UUID> {
    List<ExpenseJpaEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
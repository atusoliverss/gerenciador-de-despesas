package com.atus.gerdp.infrastructure.persistence.jpa.repositories;

import com.atus.gerdp.infrastructure.persistence.jpa.entities.BudgetJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataBudgetRepository extends JpaRepository<BudgetJpaEntity, UUID> {
    Optional<BudgetJpaEntity> findByCategory_IdAndPeriod(UUID categoryId, YearMonth period);
    
    List<BudgetJpaEntity> findByPeriod(YearMonth period);

    @Query("SELECT DISTINCT b.period FROM BudgetJpaEntity b ORDER BY b.period DESC")
    List<YearMonth> findDistinctPeriods();
}
package com.atus.gerdp.infrastructure.persistence.jpa.repositories;

import com.atus.gerdp.infrastructure.persistence.jpa.entities.CategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface SpringDataCategoryRepository extends JpaRepository<CategoryJpaEntity, UUID> {
}
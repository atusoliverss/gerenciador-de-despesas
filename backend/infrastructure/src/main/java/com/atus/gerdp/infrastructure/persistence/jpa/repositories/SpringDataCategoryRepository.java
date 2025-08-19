package com.atus.gerdp.infrastructure.persistence.jpa.repositories;

import com.atus.gerdp.infrastructure.persistence.jpa.entities.CategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

/**
 * Interface do Spring Data JPA para a entidade Category.
 * Fornece os métodos CRUD básicos de graça.
 */
@Repository
public interface SpringDataCategoryRepository extends JpaRepository<CategoryJpaEntity, UUID> {
}
package com.atus.gerdp.infrastructure.persistence.repositories;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.domain.entities.Category;
import com.atus.gerdp.infrastructure.persistence.jpa.repositories.SpringDataCategoryRepository;
import com.atus.gerdp.infrastructure.persistence.mappers.CategoryPersistenceMapper;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementação do contrato CategoryRepository.
 * Usa o Spring Data JPA para de fato interagir com o banco de dados.
 */
@Component
public class CategoryRepositoryImpl implements CategoryRepository {
    private final SpringDataCategoryRepository springDataRepository;
    private final CategoryPersistenceMapper mapper = new CategoryPersistenceMapper();

    public CategoryRepositoryImpl(SpringDataCategoryRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Category save(Category category) {
        var jpaEntity = mapper.toJpaEntity(category);
        var savedEntity = springDataRepository.save(jpaEntity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return springDataRepository.findAll().stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        springDataRepository.deleteById(id);
    }
}
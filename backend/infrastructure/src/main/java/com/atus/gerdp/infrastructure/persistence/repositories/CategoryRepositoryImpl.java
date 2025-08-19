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

@Component
public class CategoryRepositoryImpl implements CategoryRepository {
    private final SpringDataCategoryRepository r;
    private final CategoryPersistenceMapper m = new CategoryPersistenceMapper();

    public CategoryRepositoryImpl(SpringDataCategoryRepository r) {
        this.r = r;
    }

    @Override
    public Category save(Category c) {
        var j = m.toJpaEntity(c);
        var s = r.save(j);
        return m.toDomain(s);
    }

    @Override
    public Optional<Category> findById(UUID id) {
        return r.findById(id).map(m::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return r.findAll().stream().map(m::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        r.deleteById(id);
    }
}
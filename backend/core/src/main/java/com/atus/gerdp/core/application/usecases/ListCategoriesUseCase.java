package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ListCategoriesUseCase {
    private final CategoryRepository categoryRepository;

    public ListCategoriesUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Output> execute() {
        return categoryRepository.findAll().stream()
                .map(category -> new Output(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    public record Output(UUID id, String name) {
    }
}
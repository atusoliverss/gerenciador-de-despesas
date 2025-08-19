package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.domain.entities.Category;

import java.util.UUID;

public class UpdateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    public UpdateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void execute(Input input) {
        // Busca a categoria existente
        Category category = categoryRepository.findById(input.id())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + input.id()));

        // Atualiza os dados
        category.setName(input.name());

        // Salva a entidade atualizada. O JPA entende que é um UPDATE por causa do ID existente.
        categoryRepository.save(category);
    }

    public record Input(UUID id, String name) {}
}
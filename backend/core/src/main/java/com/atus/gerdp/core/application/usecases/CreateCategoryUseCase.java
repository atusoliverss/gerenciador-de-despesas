package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.domain.entities.Category;
import java.util.UUID;

public class CreateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    public CreateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Output execute(Input input) {
        var newCategory = Category.builder().id(UUID.randomUUID()).name(input.name()).build();
        var savedCategory = this.categoryRepository.save(newCategory);
        return new Output(savedCategory.getId(), savedCategory.getName());
    }

    public record Input(String name) {
    }

    public record Output(UUID id, String name) {
    }
}
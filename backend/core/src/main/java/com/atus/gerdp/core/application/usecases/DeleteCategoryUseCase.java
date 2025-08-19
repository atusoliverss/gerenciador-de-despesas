package com.atus.gerdp.core.application.usecases;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import java.util.UUID;

public class DeleteCategoryUseCase {
    private final CategoryRepository categoryRepository;

    public DeleteCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void execute(UUID id) {
        categoryRepository.deleteById(id);
    }
}
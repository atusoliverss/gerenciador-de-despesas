package com.atus.gerdp.core.application.usecases.category;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import java.util.UUID;

/**
 * Caso de Uso para deletar uma categoria.
 */
public class DeleteCategoryUseCase {
    private final CategoryRepository categoryRepository;

    public DeleteCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Executa a lógica de deleção.
     */
    public void execute(UUID id) {
        categoryRepository.deleteById(id);
    }
}
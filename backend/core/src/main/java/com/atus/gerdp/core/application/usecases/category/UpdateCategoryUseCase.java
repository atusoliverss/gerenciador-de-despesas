package com.atus.gerdp.core.application.usecases.category;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.domain.entities.Category;

import java.util.UUID;

/**
 * Caso de Uso para atualizar uma categoria existente.
 */
public class UpdateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    public UpdateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Executa a lógica de atualização.
     */
    public void execute(Input input) {
        // Busca a categoria existente ou lança um erro se não encontrar
        Category category = categoryRepository.findById(input.id())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + input.id()));

        // Atualiza o nome da categoria
        category.setName(input.name());

        // Salva a categoria com os dados atualizados
        categoryRepository.save(category);
    }

    // Dados de entrada para o caso de uso
    public record Input(UUID id, String name) {}
}
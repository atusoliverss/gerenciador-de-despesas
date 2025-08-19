package com.atus.gerdp.core.application.usecases.category;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import com.atus.gerdp.core.domain.entities.Category;
import java.util.UUID;

/**
 * Caso de Uso para criar uma nova categoria.
 */
public class CreateCategoryUseCase {
    private final CategoryRepository categoryRepository;

    public CreateCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Executa a lógica de criação.
     */
    public Output execute(Input input) {
        var newCategory = Category.builder()
                .id(UUID.randomUUID())
                .name(input.name())
                .build();
        
        var savedCategory = this.categoryRepository.save(newCategory);
        
        return new Output(savedCategory.getId(), savedCategory.getName());
    }

    // Dados de entrada para o caso de uso
    public record Input(String name) {}

    // Dados de saída do caso de uso
    public record Output(UUID id, String name) {}
}
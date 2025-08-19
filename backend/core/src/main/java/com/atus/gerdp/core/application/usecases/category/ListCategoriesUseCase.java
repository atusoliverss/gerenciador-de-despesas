package com.atus.gerdp.core.application.usecases.category;

import com.atus.gerdp.core.application.repositories.CategoryRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Caso de Uso para listar todas as categorias.
 */
public class ListCategoriesUseCase {
    private final CategoryRepository categoryRepository;

    public ListCategoriesUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Executa a busca por todas as categorias.
     */
    public List<Output> execute() {
        return categoryRepository.findAll().stream()
                .map(category -> new Output(category.getId(), category.getName()))
                .collect(Collectors.toList());
    }

    // Dados de saída do caso de uso
    public record Output(UUID id, String name) {}
}
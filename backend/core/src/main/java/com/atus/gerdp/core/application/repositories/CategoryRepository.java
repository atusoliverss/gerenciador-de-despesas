package com.atus.gerdp.core.application.repositories;

import com.atus.gerdp.core.domain.entities.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Define as operações de banco de dados para as Categorias.
 * É um "contrato" que a camada de infraestrutura deve implementar.
 */
public interface CategoryRepository {

    /**
     * Salva ou atualiza uma categoria.
     */
    Category save(Category category);

    /**
     * Busca uma categoria pelo seu ID.
     */
    Optional<Category> findById(UUID id);

    /**
     * Lista todas as categorias.
     */
    List<Category> findAll();

    /**
     * Deleta uma categoria pelo seu ID.
     */
    void deleteById(UUID id);
}

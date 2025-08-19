package com.atus.gerdp.core.application.repositories;

import com.atus.gerdp.core.domain.entities.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Esta interface é um CONTRATO.
 * Ela diz: "Qualquer um que queira ser um repositório de categorias,
 * PRECISA saber como salvar uma categoria e como buscá-la por ID".
 * A implementação real (com código de banco de dados) será feita depois, no
 * módulo 'infrastructure'.
 */
public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(UUID id);

    List<Category> findAll();

    void deleteById(UUID id);
}
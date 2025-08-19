package com.atus.gerdp.infrastructure.web.controllers;

import com.atus.gerdp.core.application.usecases.category.CreateCategoryUseCase;
import com.atus.gerdp.core.application.usecases.category.DeleteCategoryUseCase;
import com.atus.gerdp.core.application.usecases.category.ListCategoriesUseCase;
import com.atus.gerdp.core.application.usecases.category.UpdateCategoryUseCase;
import com.atus.gerdp.infrastructure.web.dto.CreateCategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Controller que gerencia os endpoints da API para Categorias.
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CreateCategoryUseCase createCategoryUseCase;
    private final ListCategoriesUseCase listCategoriesUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;

    public CategoryController(CreateCategoryUseCase createCategoryUseCase, ListCategoriesUseCase listCategoriesUseCase, DeleteCategoryUseCase deleteCategoryUseCase, UpdateCategoryUseCase updateCategoryUseCase) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.listCategoriesUseCase = listCategoriesUseCase;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.updateCategoryUseCase = updateCategoryUseCase;
    }

    /**
     * Endpoint para criar uma nova categoria.
     * Rota: POST /categories
     */
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateCategoryRequest request) {
        var input = new CreateCategoryUseCase.Input(request.name());
        var output = createCategoryUseCase.execute(input);
        return ResponseEntity.created(URI.create("/categories/" + output.id())).build();
    }

    /**
     * Endpoint para listar todas as categorias.
     * Rota: GET /categories
     */
    @GetMapping
    public ResponseEntity<List<ListCategoriesUseCase.Output>> listAll() {
        return ResponseEntity.ok(listCategoriesUseCase.execute());
    }

    /**
     * Endpoint para deletar uma categoria.
     * Rota: DELETE /categories/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteCategoryUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para atualizar uma categoria.
     * Rota: PUT /categories/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody CreateCategoryRequest request) {
        var input = new UpdateCategoryUseCase.Input(id, request.name());
        updateCategoryUseCase.execute(input);
        return ResponseEntity.ok().build();
    }
}
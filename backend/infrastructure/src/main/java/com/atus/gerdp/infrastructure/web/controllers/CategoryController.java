package com.atus.gerdp.infrastructure.web.controllers;

import com.atus.gerdp.core.application.usecases.*;
import com.atus.gerdp.infrastructure.web.dto.CreateCategoryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CreateCategoryUseCase create;
    private final ListCategoriesUseCase list;
    private final DeleteCategoryUseCase delete;
    private final UpdateCategoryUseCase update;

    public CategoryController(CreateCategoryUseCase c, ListCategoriesUseCase l, DeleteCategoryUseCase d, UpdateCategoryUseCase u) {
        this.create = c;
        this.list = l;
        this.delete = d;
        this.update = u;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateCategoryRequest r) {
        var i = new CreateCategoryUseCase.Input(r.name());
        var o = create.execute(i);
        return ResponseEntity.created(URI.create("/categories/" + o.id())).build();
    }

    @GetMapping
    public ResponseEntity<List<ListCategoriesUseCase.Output>> listAll() {
        return ResponseEntity.ok(list.execute());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        delete.execute(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody CreateCategoryRequest request) {
        var input = new UpdateCategoryUseCase.Input(id, request.name());
        update.execute(input);
        return ResponseEntity.ok().build();
    }
}
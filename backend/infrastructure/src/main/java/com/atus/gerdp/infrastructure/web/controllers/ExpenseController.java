package com.atus.gerdp.infrastructure.web.controllers;

import com.atus.gerdp.core.application.usecases.expense.DeleteExpenseUseCase;
import com.atus.gerdp.core.application.usecases.expense.ListExpensesUseCase;
import com.atus.gerdp.core.application.usecases.expense.RegisterExpenseUseCase;
import com.atus.gerdp.core.application.usecases.expense.UpdateExpenseUseCase;
import com.atus.gerdp.infrastructure.web.dto.RegisterExpenseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Controller que gerencia os endpoints da API para Despesas (Expenses).
 */
@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final RegisterExpenseUseCase registerExpenseUseCase;
    private final ListExpensesUseCase listExpensesUseCase;
    private final DeleteExpenseUseCase deleteExpenseUseCase;
    private final UpdateExpenseUseCase updateExpenseUseCase;

    public ExpenseController(RegisterExpenseUseCase registerExpenseUseCase, ListExpensesUseCase listExpensesUseCase, DeleteExpenseUseCase deleteExpenseUseCase, UpdateExpenseUseCase updateExpenseUseCase) {
        this.registerExpenseUseCase = registerExpenseUseCase;
        this.listExpensesUseCase = listExpensesUseCase;
        this.deleteExpenseUseCase = deleteExpenseUseCase;
        this.updateExpenseUseCase = updateExpenseUseCase;
    }

    /**
     * Endpoint para criar uma nova despesa.
     * Rota: POST /expenses
     */
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody RegisterExpenseRequest request) {
        var input = new RegisterExpenseUseCase.Input(request.description(), request.amount(), request.date(), request.categoryId());
        var output = registerExpenseUseCase.execute(input);
        return ResponseEntity.created(URI.create("/expenses/" + output.id())).build();
    }

    /**
     * Endpoint para listar todas as despesas.
     * Rota: GET /expenses
     */
    @GetMapping
    public ResponseEntity<List<ListExpensesUseCase.Output>> listAll() {
        return ResponseEntity.ok(listExpensesUseCase.execute());
    }

    /**
     * Endpoint para deletar uma despesa.
     * Rota: DELETE /expenses/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteExpenseUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para atualizar uma despesa.
     * Rota: PUT /expenses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody RegisterExpenseRequest request) {
        var input = new UpdateExpenseUseCase.Input(
                id,
                request.description(),
                request.amount(),
                request.date(),
                request.categoryId()
        );
        updateExpenseUseCase.execute(input);
        return ResponseEntity.ok().build();
    }
}

package com.atus.gerdp.infrastructure.web.controllers;

import com.atus.gerdp.core.application.usecases.*;
import com.atus.gerdp.infrastructure.web.dto.RegisterExpenseRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final RegisterExpenseUseCase r;
    private final ListExpensesUseCase l;
    private final DeleteExpenseUseCase d;
    private final UpdateExpenseUseCase u;

    public ExpenseController(RegisterExpenseUseCase r, ListExpensesUseCase l, DeleteExpenseUseCase d, UpdateExpenseUseCase u) {
        this.r = r;
        this.l = l;
        this.d = d;
        this.u = u;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody RegisterExpenseRequest req) {
        var i = new RegisterExpenseUseCase.Input(req.description(), req.amount(), req.date(), req.categoryId());
        var o = r.execute(i);
        return ResponseEntity.created(URI.create("/expenses/" + o.id())).build();
    }

    @GetMapping
    public ResponseEntity<List<ListExpensesUseCase.Output>> listAll() {
        return ResponseEntity.ok(l.execute());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        d.execute(id);
        return 
        ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody RegisterExpenseRequest request) {
        var input = new UpdateExpenseUseCase.Input(
                id,
                request.description(),
                request.amount(),
                request.date(),
                request.categoryId()
        );
        u.execute(input);
        return ResponseEntity.ok().build();
    }
}
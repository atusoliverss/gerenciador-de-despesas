package com.atus.gerdp.infrastructure.web.controllers;

import com.atus.gerdp.core.application.usecases.CreateOrUpdateBudgetUseCase;
import com.atus.gerdp.core.application.usecases.DeleteBudgetUseCase;
import com.atus.gerdp.core.application.usecases.ListAvailableBudgetPeriodsUseCase;
import com.atus.gerdp.core.application.usecases.ListBudgetsUseCase;
import com.atus.gerdp.infrastructure.web.dto.CreateOrUpdateBudgetRequest;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/budgets")
public class BudgetController {
    private final CreateOrUpdateBudgetUseCase u;
    private final ListBudgetsUseCase l;
    private final DeleteBudgetUseCase d;
    private final ListAvailableBudgetPeriodsUseCase listPeriods;

    public BudgetController(CreateOrUpdateBudgetUseCase u, ListBudgetsUseCase l, DeleteBudgetUseCase d, ListAvailableBudgetPeriodsUseCase listPeriods) {
        this.u = u;
        this.l = l;
        this.d = d;
        this.listPeriods = listPeriods;
    }

    @PostMapping
    public ResponseEntity<Void> createOrUpdate(@RequestBody CreateOrUpdateBudgetRequest r) {
        var i = new CreateOrUpdateBudgetUseCase.Input(r.amount(), r.period(), r.categoryId());
        u.execute(i);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ListBudgetsUseCase.Output>> listByPeriod(@RequestParam("period") YearMonth period) {
        var input = new ListBudgetsUseCase.Input(period);
        var output = l.execute(input);
        return ResponseEntity.ok(output);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        d.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/periods")
    public ResponseEntity<List<YearMonth>> listAvailablePeriods() {
        var periods = listPeriods.execute();
        return ResponseEntity.ok(periods);
    }
}
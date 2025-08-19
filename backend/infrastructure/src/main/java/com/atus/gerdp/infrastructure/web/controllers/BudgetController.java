package com.atus.gerdp.infrastructure.web.controllers;

import com.atus.gerdp.core.application.usecases.budget.CreateOrUpdateBudgetUseCase;
import com.atus.gerdp.core.application.usecases.budget.DeleteBudgetUseCase;
import com.atus.gerdp.core.application.usecases.budget.ListAvailableBudgetPeriodsUseCase;
import com.atus.gerdp.core.application.usecases.budget.ListBudgetsUseCase;
import com.atus.gerdp.infrastructure.web.dto.CreateOrUpdateBudgetRequest;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller que gerencia os endpoints da API para Orçamentos (Budgets).
 * Recebe as requisições web e as delega para os casos de uso apropriados.
 */
@RestController
@RequestMapping("/budgets")
public class BudgetController {
    private final CreateOrUpdateBudgetUseCase createOrUpdateBudgetUseCase;
    private final ListBudgetsUseCase listBudgetsUseCase;
    private final DeleteBudgetUseCase deleteBudgetUseCase;
    private final ListAvailableBudgetPeriodsUseCase listAvailableBudgetPeriodsUseCase;

    public BudgetController(CreateOrUpdateBudgetUseCase createOrUpdateBudgetUseCase, ListBudgetsUseCase listBudgetsUseCase, DeleteBudgetUseCase deleteBudgetUseCase, ListAvailableBudgetPeriodsUseCase listAvailableBudgetPeriodsUseCase) {
        this.createOrUpdateBudgetUseCase = createOrUpdateBudgetUseCase;
        this.listBudgetsUseCase = listBudgetsUseCase;
        this.deleteBudgetUseCase = deleteBudgetUseCase;
        this.listAvailableBudgetPeriodsUseCase = listAvailableBudgetPeriodsUseCase;
    }

    /**
     * Endpoint para criar ou atualizar um orçamento.
     * Rota: POST /budgets
     */
    @PostMapping
    public ResponseEntity<Void> createOrUpdate(@RequestBody CreateOrUpdateBudgetRequest request) {
        var input = new CreateOrUpdateBudgetUseCase.Input(request.amount(), request.period(), request.categoryId());
        createOrUpdateBudgetUseCase.execute(input);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint para listar os orçamentos de um mês específico.
     * Rota: GET /budgets?period=YYYY-MM
     */
    @GetMapping
    public ResponseEntity<List<ListBudgetsUseCase.Output>> listByPeriod(@RequestParam("period") YearMonth period) {
        var input = new ListBudgetsUseCase.Input(period);
        var output = listBudgetsUseCase.execute(input);
        return ResponseEntity.ok(output);
    }

    /**
     * Endpoint para deletar um orçamento.
     * Rota: DELETE /budgets/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteBudgetUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Endpoint para listar os meses que possuem orçamentos.
     * Rota: GET /budgets/periods
     */
    @GetMapping("/periods")
    public ResponseEntity<List<YearMonth>> listAvailablePeriods() {
        var periods = listAvailableBudgetPeriodsUseCase.execute();
        return ResponseEntity.ok(periods);
    }
}
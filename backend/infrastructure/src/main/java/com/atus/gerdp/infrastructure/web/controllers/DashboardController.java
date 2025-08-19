package com.atus.gerdp.infrastructure.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.atus.gerdp.core.application.usecases.dashboard.GetDashboardSummaryUseCase;
import java.time.YearMonth;

/**
 * Controller que gerencia os endpoints da API para o Dashboard.
 */
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final GetDashboardSummaryUseCase getDashboardSummaryUseCase;

    public DashboardController(GetDashboardSummaryUseCase getDashboardSummaryUseCase) {
        this.getDashboardSummaryUseCase = getDashboardSummaryUseCase;
    }

    /**
     * Endpoint para buscar o resumo de dados de um mês para o dashboard.
     * Rota: GET /dashboard/summary?period=YYYY-MM
     */
    @GetMapping("/summary")
    public ResponseEntity<GetDashboardSummaryUseCase.DashboardOutput> getSummary(
            @RequestParam("period") YearMonth period
    ) {
        var input = new GetDashboardSummaryUseCase.Input(period);
        var output = getDashboardSummaryUseCase.execute(input);
        return ResponseEntity.ok(output);
    }
}
package com.atus.gerdp.infrastructure.web.controllers;

import com.atus.gerdp.core.application.usecases.GetDashboardSummaryUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final GetDashboardSummaryUseCase getDashboardSummaryUseCase;

    public DashboardController(GetDashboardSummaryUseCase getDashboardSummaryUseCase) {
        this.getDashboardSummaryUseCase = getDashboardSummaryUseCase;
    }

    @GetMapping("/summary")
    public ResponseEntity<GetDashboardSummaryUseCase.DashboardOutput> getSummary(
            @RequestParam("period") YearMonth period
    ) {
        var input = new GetDashboardSummaryUseCase.Input(period);
        var output = getDashboardSummaryUseCase.execute(input);
        return ResponseEntity.ok(output);
    }
}
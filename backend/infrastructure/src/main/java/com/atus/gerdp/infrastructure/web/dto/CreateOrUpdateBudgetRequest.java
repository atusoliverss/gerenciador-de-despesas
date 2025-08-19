package com.atus.gerdp.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

public record CreateOrUpdateBudgetRequest(BigDecimal amount, YearMonth period, UUID categoryId) {
}
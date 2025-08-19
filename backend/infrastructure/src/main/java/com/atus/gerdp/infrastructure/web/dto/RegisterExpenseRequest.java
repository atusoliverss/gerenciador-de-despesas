package com.atus.gerdp.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegisterExpenseRequest(String description, BigDecimal amount, LocalDate date, UUID categoryId) {
}
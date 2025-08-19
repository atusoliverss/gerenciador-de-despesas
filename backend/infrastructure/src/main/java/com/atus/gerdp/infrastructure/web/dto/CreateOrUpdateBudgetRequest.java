package com.atus.gerdp.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

/**
 * DTO para criar ou atualizar um orçamento.
 * Define os dados que o frontend envia para a API.
 */
public record CreateOrUpdateBudgetRequest(
    BigDecimal amount, 
    YearMonth period, 
    UUID categoryId
) {}
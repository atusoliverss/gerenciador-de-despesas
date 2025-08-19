package com.atus.gerdp.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Budget {
    private UUID id;
    private BigDecimal amount;

    // YearMonth é perfeito para representar um período mensal (ex: 2025-08)
    private YearMonth period;

    private UUID categoryId;
}
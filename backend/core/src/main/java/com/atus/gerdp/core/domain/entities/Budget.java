package com.atus.gerdp.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

/**
 * Representa um Orçamento (Budget).
 * Define uma meta de gastos para uma categoria em um mês específico.
 */
@Data
@Builder
@AllArgsConstructor
public class Budget {
    /** ID único do orçamento. */
    private UUID id;

    /** Valor monetário do orçamento. */
    private BigDecimal amount;

    /** Mês e ano ao qual o orçamento se aplica (ex: 2025-08). */
    private YearMonth period;

    /** ID da categoria à qual este orçamento pertence. */
    private UUID categoryId;
}
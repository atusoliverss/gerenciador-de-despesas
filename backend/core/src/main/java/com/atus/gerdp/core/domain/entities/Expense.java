package com.atus.gerdp.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Representa uma Despesa (Expense).
 * É um registro de um gasto realizado.
 */
@Data
@Builder
@AllArgsConstructor
public class Expense {
    /** ID único da despesa. */
    private UUID id;

    /** Descrição do gasto (ex: "Almoço no restaurante"). */
    private String description;

    /** Valor monetário da despesa. */
    private BigDecimal amount;

    /** Data em que a despesa ocorreu. */
    private LocalDate date;

    /** ID da categoria à qual esta despesa pertence. */
    private UUID categoryId;
}
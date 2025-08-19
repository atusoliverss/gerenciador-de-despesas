package com.atus.gerdp.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class Expense {
    private UUID id;
    private String description;

    // Usamos BigDecimal para valores monetários para evitar erros de
    // arredondamento.
    private BigDecimal amount;

    // Usamos LocalDate para representar uma data sem tempo.
    private LocalDate date;

    // A despesa pertence a uma categoria, então guardamos o ID dela.
    private UUID categoryId;
}
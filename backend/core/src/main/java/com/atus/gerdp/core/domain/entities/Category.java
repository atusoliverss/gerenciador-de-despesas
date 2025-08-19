package com.atus.gerdp.core.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Representa uma Categoria de gastos.
 * Ex: Alimentação, Transporte, Lazer.
 */
@Data
@Builder
@AllArgsConstructor
public class Category {
    /** ID único da categoria. */
    private UUID id;

    /** Nome da categoria. */
    private String name;
}
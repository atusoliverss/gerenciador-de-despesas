package com.atus.gerdp.infrastructure.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO para registrar uma nova despesa.
 * Define os dados que o frontend envia para a API.
 */
public record RegisterExpenseRequest(
    String description, 
    BigDecimal amount, 
    LocalDate date, 
    UUID categoryId
) {}
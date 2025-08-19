package com.atus.gerdp.infrastructure.web.dto;

// Usamos 'record' para DTOs simples. É conciso e imutável.
public record CreateCategoryRequest(String name) {
}
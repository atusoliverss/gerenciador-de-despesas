package com.atus.gerdp.infrastructure.web.dto;

/**
 * DTO (Data Transfer Object) para criar uma nova categoria.
 * Define os dados que o frontend envia para a API.
 * 'record' é uma forma moderna e concisa de criar classes de dados imutáveis.
 */
public record CreateCategoryRequest(String name) {
}
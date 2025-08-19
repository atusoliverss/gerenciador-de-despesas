package com.atus.gerdp.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

/**
 * Representa a tabela "categories" no banco de dados.
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryJpaEntity {
    /** Chave primária da tabela. */
    @Id
    private UUID id;

    /** Coluna para o nome da categoria. Não pode ser nulo e deve ser único. */
    @Column(nullable = false, unique = true)
    private String name;
}
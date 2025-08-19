package com.atus.gerdp.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Representa a tabela "expenses" no banco de dados.
 */
@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseJpaEntity {
    /** Chave primária da tabela. */
    @Id
    private UUID id;

    /** Coluna para a descrição da despesa. */
    @Column(nullable = false)
    private String description;

    /** Coluna para o valor da despesa. */
    @Column(nullable = false)
    private BigDecimal amount;

    /** Coluna para a data da despesa. */
    @Column(nullable = false)
    private LocalDate date;

    /** Relacionamento: Muitas despesas podem pertencer a UMA categoria. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false) // Chave estrangeira para a tabela de categorias.
    private CategoryJpaEntity category;
}
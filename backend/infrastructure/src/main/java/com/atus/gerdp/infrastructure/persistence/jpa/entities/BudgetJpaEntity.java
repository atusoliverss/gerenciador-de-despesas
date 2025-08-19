package com.atus.gerdp.infrastructure.persistence.jpa.entities;

import com.atus.gerdp.infrastructure.persistence.converters.YearMonthAttributeConverter;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

/**
 * Representa a tabela "budgets" no banco de dados.
 * Esta classe é usada pelo JPA para criar e interagir com a tabela.
 */
@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetJpaEntity {
    /** Chave primária da tabela. */
    @Id
    private UUID id;

    /** Coluna para o valor do orçamento. */
    @Column(nullable = false)
    private BigDecimal amount;

    /** Coluna para o período (mês/ano), usando nosso conversor customizado. */
    @Column(nullable = false, length = 7)
    @Convert(converter = YearMonthAttributeConverter.class)
    private YearMonth period;

    /** Relacionamento: Muitos orçamentos podem pertencer a UMA categoria. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false) // Chave estrangeira para a tabela de categorias.
    private CategoryJpaEntity category;
}
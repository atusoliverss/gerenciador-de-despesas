package com.atus.gerdp.infrastructure.persistence.jpa.entities;

import com.atus.gerdp.infrastructure.persistence.converters.YearMonthAttributeConverter;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Entity
@Table(name = "budgets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BudgetJpaEntity {
    @Id
    private UUID id;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false, length = 7)
    @Convert(converter = YearMonthAttributeConverter.class)
    private YearMonth period;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryJpaEntity category;
}
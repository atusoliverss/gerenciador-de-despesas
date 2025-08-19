package com.atus.gerdp.infrastructure.persistence.jpa.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryJpaEntity {
    @Id
    private UUID id;
    @Column(nullable = false, unique = true)
    private String name;
}
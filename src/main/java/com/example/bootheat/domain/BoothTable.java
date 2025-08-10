package com.example.bootheat.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name="booth_table",
        uniqueConstraints = @UniqueConstraint(columnNames = {"booth_id","table_number"}))
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class BoothTable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="table_id")
    private Long tableId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="booth_id", nullable=false)
    private Booth booth;

    @Column(name="table_number", nullable=false)
    private Integer tableNumber;

    @Builder.Default
    private Boolean active = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}

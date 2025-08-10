package com.example.bootheat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "table_visit")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TableVisit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="visit_id")
    private Long visitId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="table_id", nullable=false)
    private BoothTable table;

    @Column(name="visit_no", nullable=false)
    private Integer visitNo;

    @Column(nullable=false)
    private String status; // OPEN / CLOSED

    @Column(name="started_at", nullable=false)
    @Builder.Default
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name="closed_at")
    private LocalDateTime closedAt;
}

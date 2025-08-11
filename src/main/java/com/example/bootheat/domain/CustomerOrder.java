package com.example.bootheat.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "customer_order", indexes = {
        @Index(name = "idx_order_status_approved", columnList = "status, approved_at")
})
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CustomerOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="booth_id", nullable=false)
    private Booth booth;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="table_id", nullable=false)
    private BoothTable table;

    // NEW: table_visit FK
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="visit_id", nullable=false)
    private TableVisit visit;

    @Column(nullable=false) private String status; // PENDING/APPROVED/REJECTED
    @Column(name="order_code", unique=true, nullable = true) private String orderCode;
    @Column(nullable=false) private Integer totalAmount;

    @Column(nullable=false) @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name="approved_at")
    private LocalDateTime approvedAt;
}

package com.example.bootheat.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_info", uniqueConstraints = @UniqueConstraint(columnNames = {"order_id"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentInfo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_info_id")
    private Long paymentInfoId;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name="order_id", nullable=false)
    private CustomerOrder order;

    @Column(nullable=false) private String payerName;
    @Column(nullable=false) private Integer amount;

    @Builder.Default
    @Column(nullable=false) private LocalDateTime paidAt = LocalDateTime.now();
}

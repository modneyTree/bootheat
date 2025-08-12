// domain/ManagerUser.java
package com.example.bootheat.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "manager_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"booth_id"}),   // 부스당 1명 정책
                @UniqueConstraint(columnNames = {"username"})
        }
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ManagerUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booth_id", nullable = false)
    private Booth booth;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, length = 20)
    private String role; // MANAGER | ADMIN
    // ★ 복원
    @Column(length = 200)
    private String account; // "카뱅 3333-..." 형태

    @Column(length = 50)
    private String accountBank;

    @Column(length = 200)
    private String accountNo;

    @Column(length = 100)
    private String accountHolder;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

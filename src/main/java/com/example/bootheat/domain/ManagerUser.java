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
                @UniqueConstraint(columnNames = {"booth_id"}),   // ★ 부스당 매니저 1명
                @UniqueConstraint(columnNames = {"username"})    // 이미 있던 의미와 동일(전역 유니크)
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
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

    @CreationTimestamp
    private LocalDateTime createdAt;
}

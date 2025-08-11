package com.example.bootheat.domain;

import com.example.bootheat.domain.Booth;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booth_id")
    private Booth booth;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false, length = 20)
    private String role; // MANAGER | ADMIN(옵션)

//    // ★ 추가
//    @Deprecated
//    @Column(length = 200)
//    private String account; // 예: "카뱅 3333-12-3456789 (홍길동)"

    @CreationTimestamp
    private LocalDateTime createdAt;
}
